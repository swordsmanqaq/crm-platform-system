package com.heng.mkt.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.heng.base.utils.LoginContext;
import com.heng.mkt.domain.*;
import com.heng.mkt.dto.ClueActivityDto;
import com.heng.mkt.dto.ClueBusinessDto;
import com.heng.mkt.mapper.*;
import com.heng.mkt.service.IClueRemarkService;
import com.heng.mkt.service.IClueService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Employee;
import com.heng.prod.domain.Product;
import com.heng.prod.mapper.ProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
@Service
public class ClueServiceImpl extends BaseServiceImpl<Clue> implements IClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper; ;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private BusinessRemarkMapper businessRemarkMapper;

    @Autowired
    private BusinessProductMapper businessProductMapper;


    @Override
    @Transactional
    public void saveActivity(ClueActivityDto dto) {
        clueMapper.deleteActivityByClueId(dto.getClueId());
        clueMapper.saveActivity(dto.getClueId(),dto.getActivityId());
    }

    @Override
    @Transactional
    public void saveClueAssign(Clue clue, Employee loginUser) {
        clue.setState(1);
        clue.setEditBy(loginUser.getUsername());
        clue.setEditTime(new Date());
        clueMapper.update(clue);
        String content = "%s分配了人员：%s给%s";
        content = String.format(content, loginUser.getUsername(),clue.getFullName(),clue.getEmployee().getUsername());
        saveClueRemark(clue,loginUser,content);
    }

    @Override
    @Transactional
    public void saveClueFollow(Clue clue, Employee loginUser) {
        clue.setEditBy(loginUser.getUsername());
        clue.setEditTime(new Date());
        clueMapper.update(clue);
        String content = "%s跟进了线索：跟进内容：%s和下次联系时间%s";
        content = String.format(content, loginUser.getUsername(),clue.getContactSummary(),clue.getNextContactTime());
        saveClueRemark(clue,loginUser,content);
    }

    @Override
    @Transactional
    public void saveClueBusiness(ClueBusinessDto dto, Employee loginUser) {
        Clue clue = dto.getClue();
        Long productId = dto.getProduct().getId();
        Product product = productMapper.loadById(productId);

        //改变状态
        clue.setState(2);
        clue.setEditBy(loginUser.getUsername());
        clue.setEditTime(new Date());
        clueMapper.update(clue);

        //添加线索记录
        String content="客户%s意向购买%s,操作人：%s";
        content=String.format(content,clue.getFullName(),product.getName(),loginUser.getUsername());
        saveClueRemark(clue,loginUser,content);

        //添加商机
        Business business = new Business();
        business.setName(String.format("客户%s意向购买%s",clue.getFullName(),product.getName()));
        business.setClueId(clue.getId());
        business.setProductId(product.getId());
        business.setProductName(product.getName());
        business.setProductPrice(product.getPrice());
        businessMapper.insert(business);

        //添加商机记录
        BusinessRemark businessRemark = new BusinessRemark();
        businessRemark.setBisinessId(business.getId());
        businessRemark.setNoteContent(content);
        businessRemark.setCreateBy(loginUser.getUsername());
        businessRemark.setCreateTime(new Date());
        businessRemarkMapper.insert(businessRemark);

        //添加商机产品
        BusinessProduct businessProduct = new BusinessProduct();
        BeanUtils.copyProperties(product, businessProduct);
        businessProduct.setBusinessId(business.getId());
        businessProductMapper.insert(businessProduct);

    }

    @Override
    @Transactional
    public void clueScrap(Clue clue, Employee loginUser) {
        clue.setState(-1);
        clue.setEditBy(loginUser.getUsername());
        clue.setEditTime(new Date());
        clueMapper.update(clue);
        String content = "%s把%s客户线索移入线索池%";
        content = String.format(content, loginUser.getUsername(),clue.getFullName());
        saveClueRemark(clue,loginUser,content);
    }



    //添加记录方法
    private void saveClueRemark(Clue clue, Employee loginUser,String content) {
        //添加记录
        ClueRemark clueRemark =new ClueRemark();
        clueRemark.setClueId(clue.getId());

        //备注的内容:xxxxx分配了线索客户xxx给xxx
        clueRemark.setNoteContent(content);
        clueRemark.setCreateBy(loginUser.getUsername());
        clueRemark.setCreateTime(new Date());
        clueRemarkMapper.insert(clueRemark);
    }
}
