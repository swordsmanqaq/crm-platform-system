package com.heng.order.service.impl;

import com.heng.aftermarket.domain.Guarantee;
import com.heng.aftermarket.mapper.GuaranteeMapper;
import com.heng.finance.domain.Deposit;
import com.heng.finance.domain.Receivables;
import com.heng.finance.domain.ReceivablesItem;
import com.heng.finance.mapper.DepositMapper;
import com.heng.finance.mapper.ReceivablesItemMapper;
import com.heng.finance.mapper.ReceivablesMapper;
import com.heng.order.domain.Contract;
import com.heng.order.domain.ContractRemark;
import com.heng.order.domain.Order;
import com.heng.order.dto.PayBalanceDTO;
import com.heng.order.mapper.ContractMapper;
import com.heng.order.mapper.ContractRemarkMapper;
import com.heng.order.mapper.OrderMapper;
import com.heng.order.service.IOrderService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Employee;
import com.heng.sys.domain.Dictionaryitem;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-16
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractRemarkMapper contractRemarkMapper;

    @Autowired
    private GuaranteeMapper guaranteeMapper;

    @Autowired
    private ReceivablesMapper receivablesMapper;

    @Autowired
    private DepositMapper depositMapper;

    @Autowired
    private ReceivablesItemMapper receivablesItemMapper;

    @Override
    @Transactional
    public void orderPayBalance(PayBalanceDTO dto, Employee loginUser) {
        Dictionaryitem payModel = dto.getPayModel();
        Order order = orderMapper.loadById(dto.getId());
        Deposit deposit = depositMapper.getOrderDeposit(dto.getId());

        // 1 修改订单状态
        order.setState(2);
        orderMapper.update(order);

        // 2 修改合同
        Contract contract = contractMapper.loadByOrderId(dto.getId());
        String content = contract.getContent();
        content += ",选择"+payModel.getTitle()+"支付";
        contract.setContent(content);
        contractMapper.update(contract);

        // 3 添加合同备注
        ContractRemark contractRemark = new ContractRemark();
        contractRemark.setContractId(contract.getId());
        contractRemark.setNoteContent(loginUser.getUsername()+"修改了合同");
        contractRemark.setCreateBy(loginUser.getUsername());
        contractRemark.setCreateTime(new Date());
        contractRemarkMapper.insert(contractRemark);

        // 4 创建保修单
        Guarantee guarantee = new Guarantee();
        guarantee.setProductId(order.getProductId());
        guarantee.setProductName(order.getProductName());
        guarantee.setOrderId(order.getId());
        guarantee.setOrderSn(order.getSn());
        guarantee.setCustomerId(order.getCustomerId());
        guarantee.setCustomerName(order.getCustomerName());
        guarantee.setStartTime(new Date());
        guarantee.setEndTime(DateUtils.addYears(new Date(),2));
        guarantee.setState(1);
        guaranteeMapper.insert(guarantee);

        // 5 创建应收款
        Receivables receivables = new Receivables();
        receivables.setContractId(contract.getId());
        receivables.setCustomerId(order.getCustomerId());
        receivables.setCustomerName(order.getCustomerName());
        receivables.setProductId(order.getProductId());
        receivables.setProductName(order.getProductName());
        // 应收款的金额 = 订单的金额
        receivables.setPrice(order.getPrice());
        receivables.setPaymentMethodId(payModel.getId());
        receivables.setCreateTime(new Date());
        receivablesMapper.insert(receivables);

        // 6 创建应收款明细  一笔订单的应收金额 = 订金 + 尾款的金额(全款/分期)
        // 6.1 先处理订金的应收款明细
        saveReceivablesItem(loginUser, order, contract.getId(),"订金", BigDecimal.valueOf(deposit.getDeposit()),deposit.getPayTime(),
                null,1,receivables.getId());

        // 6.2 尾款的应收款明细
        int payModelValue = Integer.parseInt(payModel.getValue());
        // 对于尾款来说,它的金额 = 订单的总金额 - 订金的金额
        BigDecimal price = order.getPrice().subtract(BigDecimal.valueOf(deposit.getDeposit()));
        if(payModelValue > 0){  // 说明是分期
            // 对于分期来说,其实金额计算是很复杂的,涉及到利息,这块我们先直接通过金额/分期期数就好了
            payModelValue = payModelValue*12;   // payModelValue是分期的年数,但是一般分期都是按月支付的
            // 既然是按月支付,那么每一期的金额都是一个应收款明细 每期应付金额 = (订单金额-订金金额)/分期期数  scale
            BigDecimal avePrice = price.divide(new BigDecimal(payModelValue),2, RoundingMode.HALF_UP);  // 第二个参数的意思是:保留小数点后2位
            Date date = new Date();
            for (int i=0;i<payModelValue;i++){
                // 最后应付时间,从今天开始算,每次+1
                Date lastShouldPayTime = DateUtils.addMonths(date, i + 1);
                saveReceivablesItem(loginUser,order,contract.getId(),"第"+(i+1)+"期金额",avePrice,null,
                        lastShouldPayTime,0,receivables.getId());
            }
        }else{
            // 全款支付
            saveReceivablesItem(loginUser,order,contract.getId(),"尾款",price,new Date(),
                    null,1,receivables.getId());
        }

    }

    private void saveReceivablesItem(Employee loginUser, Order order, Long contractId, String name, BigDecimal price, Date payTime, Date lastShouldPayTime, Integer state, Long receivablesId) {
        ReceivablesItem receivablesItem = new ReceivablesItem();
        receivablesItem.setName(name);
        receivablesItem.setContractId(contractId);
        receivablesItem.setCustomerId(order.getCustomerId());
        receivablesItem.setCustomerName(order.getCustomerName());
        receivablesItem.setProductId(order.getProductId());
        receivablesItem.setProductName(order.getProductName());
        receivablesItem.setPrice(price);
        receivablesItem.setPayTime(payTime);
        receivablesItem.setLastShouldPayTime(lastShouldPayTime);
        receivablesItem.setReceivablesId(receivablesId);
        receivablesItem.setState(state);
        receivablesItem.setPayee(loginUser.getId());
        receivablesItem.setPayeeName(loginUser.getUsername());
        receivablesItemMapper.insert(receivablesItem);
    }
}
