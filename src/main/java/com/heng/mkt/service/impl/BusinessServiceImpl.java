package com.heng.mkt.service.impl;

import com.heng.aftermarket.domain.Customer;
import com.heng.aftermarket.mapper.CustomerMapper;
import com.heng.finance.domain.Deposit;
import com.heng.finance.mapper.DepositMapper;
import com.heng.mkt.domain.Activity;
import com.heng.mkt.domain.Business;
import com.heng.mkt.domain.Clue;
import com.heng.mkt.dto.PayDepositDTO;
import com.heng.mkt.mapper.BusinessMapper;
import com.heng.mkt.mapper.ClueMapper;
import com.heng.mkt.service.IBusinessService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.order.domain.Contract;
import com.heng.order.domain.ContractRemark;
import com.heng.order.domain.Order;
import com.heng.order.domain.OrderProduct;
import com.heng.order.mapper.*;
import com.heng.org.domain.Employee;
import com.heng.prod.domain.Product;
import com.heng.prod.mapper.ProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
@Service
public class BusinessServiceImpl extends BaseServiceImpl<Business> implements IBusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderActivityMapper orderActivityMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Autowired
    private DepositMapper depositMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractRemarkMapper contractRemarkMapper;

    @Override
    @Transactional
    public void payDeposit(PayDepositDTO dto, Employee loginUser) {
        Long businessId = dto.getBusinessId();
        //获取所有活动id
        List<Long> activityIds = dto.getActivityIds();
        //根据businessId去查business对象
        Business business = businessMapper.loadById(businessId);
        //根据Business中的clueId查账clue对象
        Clue clue = clueMapper.loadById(business.getClueId());
        //根据商机表中产品id查找product对象
        Product product = productMapper.loadById(business.getProductId());
        //改变状态
        business.setState(1);
        businessMapper.update(business);

        // 1 创建客户
        Customer customer = new Customer();
        customer.setName(clue.getFullName());
        customer.setEmail(clue.getEmail());
        customer.setState(1);
        customer.setSellerId(clue.getOwner());
        customer.setInputTime(new Date());
        customer.setInputuserId(loginUser.getId());
        customerMapper.insert(customer);
        // 2 创建订单
        Order order = new Order();
        order.setSn(UUID.randomUUID().toString().replaceAll("-",""));
        order.setBusinessId(business.getId());
        order.setBusinessName(business.getName());
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setPrice(product.getPrice());
        order.setCustomerId(customer.getId());
        order.setCustomerName(customer.getName());
        order.setState(1);
        orderMapper.insert(order);
        // 3 创建订单活动
        //如果没有选择订单活动，则activityIds为空，就不需要创建，所以进行判断
        if (activityIds != null && activityIds.size() > 0){
            //新增到订单活动中间表
            orderActivityMapper.batchInsert(order.getId(),activityIds);
        }
        // 4 创建订单产品
        OrderProduct orderProduct = new OrderProduct();
        BeanUtils.copyProperties(product,orderProduct);
        orderProduct.setId(null);
        orderProduct.setOrderId(order.getId());
        orderProductMapper.insert(orderProduct);
        // 5 创建订金
        Deposit deposit = new Deposit();
        deposit.setName(String.format("%s缴纳了%s定金",customer.getName(),dto.getDeposit()));
        deposit.setCustomerId(customer.getId());
        deposit.setCustomerName(customer.getName());
        deposit.setDeposit(dto.getDeposit());
        deposit.setPayTime(new Date());
        deposit.setProductId(product.getId());
        deposit.setProductName(product.getName());
        deposit.setBusinessId(business.getId());
        deposit.setBusinessName(business.getName());
        deposit.setState(1);
        deposit.setOrderId(order.getId());
        deposit.setOrderSn(order.getSn());
        depositMapper.insert(deposit);
        // 6 创建合同
        Contract contract = new Contract();
        contract.setSn(UUID.randomUUID().toString().replaceAll("-",""));
        contract.setOrderId(order.getId());
        contract.setOrderSn(order.getSn());
        contract.setCustomerId(customer.getId());
        contract.setCustomerName(customer.getName());
        contract.setPrice(product.getPrice());
        contract.setDeposit(BigDecimal.valueOf(dto.getDeposit()));
        contract.setContent(String.format("%s花费%s购买了%s", order.getCustomerName(),order.getPrice(),order.getProductName()));
        contract.setDescription(String.format("%s花费%s购买了%s", order.getCustomerName(),order.getPrice(),order.getProductName()));
        contract.setCreateTime(new Date());
        contract.setState(1);
        contractMapper.insert(contract);
        // 7 创建合同记录
        ContractRemark contractRemark = new ContractRemark();
        contractRemark.setNoteContent(contract.getContent());
        contractRemark.setId(contract.getId());
        contractRemark.setCreateTime(new Date());
        contractRemark.setCreateBy(loginUser.getUsername());
        contractRemarkMapper.insert(contractRemark);
    }
}
