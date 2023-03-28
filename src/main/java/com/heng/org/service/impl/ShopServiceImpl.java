package com.heng.org.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.BaiduAuditUtils;
import com.heng.org.domain.Employee;
import com.heng.org.domain.EmployeeShop;
import com.heng.org.domain.Shop;
import com.heng.org.domain.ShopAuditLog;
import com.heng.org.dto.ShopAdminDTO;
import com.heng.org.dto.ShopRegisterDTO;
import com.heng.org.mapper.EmployeeMapper;
import com.heng.org.mapper.EmployeeShopMapper;
import com.heng.org.mapper.ShopAuditLogMapper;
import com.heng.org.mapper.ShopMapper;
import com.heng.org.service.IShopService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-27
 */
@Service
public class ShopServiceImpl extends BaseServiceImpl<Shop> implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeShopMapper employeeShopMapper;

    @Autowired
    private ShopAuditLogMapper shopAuditLogMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Transactional
    public void shopSubmission(ShopRegisterDTO shopRegisterDTO) {
        //调用参数校验方法
        validate(shopRegisterDTO);

        //店铺审核
        Shop shop1 = new Shop();
        BeanUtils.copyProperties(shopRegisterDTO, shop1);
        shop1.setRegisterTime(new Date());
        List<String> texts = new ArrayList<String>() {
            {
                add(shopRegisterDTO.getName());
                add(shopRegisterDTO.getAddress());
                add(shopRegisterDTO.getAdmin().getUsername());
            }
        };
        List<String> images = new ArrayList<String>() {
            {
                add(shopRegisterDTO.getLogo());
            }
        };
        //进行自动审核
        Map<String, Object> censor = BaiduAuditUtils.contextCensor(texts, images);
        Boolean success = (Boolean) censor.get("success" );
        //设置记录表的状态
        Integer audit = null;
        String note = "";
        if (success) {
            //审核通过
            shop1.setState(BaseConstants.Shop.STATE_WAIT_ACTIVE);
            audit = 1;
            note = "系统审核通过";
        } else {
            shop1.setState(BaseConstants.Shop.STATE_REJECT_AUDIT);
            audit = 0;
            note = (String) censor.get("message" );
        }
        //保存店铺信息
        shopMapper.insert(shop1);

        //管理员查重
        Employee emp = employeeMapper.loadByUsername(shopRegisterDTO.getAdmin().getUsername());
        Long employeeId = null;
        if (!Objects.isNull(emp)) {
            employeeId = emp.getId();
        } else {
            Employee employee = new Employee();
            BeanUtils.copyProperties(shopRegisterDTO.getAdmin(), employee);
            employee.setNickName(shopRegisterDTO.getAdmin().getUsername());
            employee.setState((long) BaseConstants.Employee.STATE_LOCK);
            employeeMapper.insert(employee);
            employeeId = employee.getId();
        }
        //中间表添加
        EmployeeShop employeeShop = new EmployeeShop();
        employeeShop.setEmployeeId(employeeId);
        employeeShop.setShopId(shop1.getId());
        employeeShop.setIsManager(BaseConstants.IsManager.STATE_NORMAL);
        employeeShopMapper.insert(employeeShop);

        //添加审核记录表
        ShopAuditLog shopAuditLog = new ShopAuditLog();
        shopAuditLog.setState(audit);
        shopAuditLog.setNote(note);
        shopAuditLog.setShopId(shop1.getId());
        shopAuditLog.setAuditTime(new Date());
        shopAuditLogMapper.insert(shopAuditLog);

        //邮箱发送
        try {
            if (success) {
                //审核成功，发送邮箱给予激活
                Long id = shop1.getId();
                String result = "<html>\n" +
                        "<body>\n" +
                        "<p>请点击下方链接完成激活</p>\n" +
                        "<a href=\"http://localhost:8081/shop/active/" + id + "\">http://localhost:8081/shop/active/" + id + "</a>" +
                        "</body>\n" +
                        "</html>";
                String email = shopRegisterDTO.getAdmin().getEmail();
                System.out.println(result);
                //创建复杂邮件对象
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                //发送复杂邮件的工具类
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8" );
                helper.setFrom("swords_man12@163.com" );
                //主题
                helper.setSubject("店铺激活" );
                //邮件内容
                helper.setText(result, true);
                //添加附件
                //helper.addAttachment("罗宾.jpg",new File("C:\\Users\\hm\\Desktop\\work\\aa.jpg"));
                //helper.addAttachment("压缩文件", new File("C:\\Users\\hm\\Desktop\\20191010\\2020-02-05-智能商贸-DAY4\\resources\\resources.zip"));
                //收件人
                helper.setTo(email);
                javaMailSender.send(mimeMessage);

            } else {
                //审核失败,发送邮箱告知
                String result = "很抱歉你的店铺审核未通过，原因:" + note;
                String email = shopRegisterDTO.getAdmin().getEmail();
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                //设置发送人
                mailMessage.setFrom("swords_man12@163.com" );
                //邮件主题
                mailMessage.setSubject("店铺激活" );
                //邮件内容
                mailMessage.setText(result);
                //收件人
                mailMessage.setTo(email);
                javaMailSender.send(mailMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 手动审核确认提交
     *
     * @param id
     */
    @Override
    @Transactional
    public void saveSuccessful(Long id) {
        Shop shopById = shopMapper.loadById(id);
        shopById.setState(BaseConstants.Shop.STATE_WAIT_ACTIVE);
        shopMapper.update(shopById);
    }

    /**
     * 驳回提交
     *
     * @param id
     */
    @Override
    @Transactional
    public void saveReject(Long id) {
        Shop shopById = shopMapper.loadById(id);
        shopById.setState(BaseConstants.Shop.STATE_REJECT_AUDIT);
        shopMapper.update(shopById);
    }

    /**
     * 邮箱激活
     *
     * @param id
     */
    @Override
    public void active(Long id) {
        Shop shopById = shopMapper.loadById(id);
        shopById.setState(BaseConstants.Shop.STATE_ACTIVE_SUCCESS);
        shopMapper.update(shopById);
    }


    //验证封装的方法
    private void validate(ShopRegisterDTO shopRegisterDTO) {
        ShopAdminDTO shopAdminDTO = shopRegisterDTO.getAdmin();
        //1.参数非空校验
        if (Objects.isNull(shopRegisterDTO) || Objects.isNull(shopAdminDTO) ||
                StringUtils.isAnyEmpty(shopAdminDTO.getEmail(), shopAdminDTO.getPhone(), shopAdminDTO.getEmail(), shopAdminDTO.getPassword(), shopAdminDTO.getConfirmPassword(),
                        shopRegisterDTO.getName(), shopRegisterDTO.getTel(), shopRegisterDTO.getAddress())) {
            throw new RuntimeException("信息不能为空，请重新提交" );
        }
        //1.2两次密码判断
        if (!shopAdminDTO.getPassword().equals(shopAdminDTO.getConfirmPassword())) {
            throw new RuntimeException("密码不正确，请重新输入" );
        }
        //1.3店铺查重
        Shop shop = shopMapper.loadByName(shopRegisterDTO.getName());
        if (shop != null) {
            throw new RuntimeException("店铺信息已经存在" );
        }

    }

}
