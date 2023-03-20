package com.heng.mkt.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.heng.mkt.domain.*;
import com.heng.mkt.dto.*;
import com.heng.mkt.mapper.*;
import com.heng.mkt.service.IClueService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Employee;
import com.heng.prod.domain.Product;
import com.heng.prod.mapper.ProductMapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

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
        business.setState(0);
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

    //获取所有活动
    @Override
    public List<Activity> getActivitys(Long typeId) {
        return clueMapper.getActivitys(typeId);
    }

    @Override
    @Transactional
    public List<ClueDataDTO> getDrawPieChartData() {
        return clueMapper.getDrawPieChartData();
    }

    @Override
    @Transactional
    public List<LineChartDTO> getDrawLineChart() {
        return clueMapper.getDrawLineChart();
    }

    //导入excel文件
    @Override
    public void importExcel(MultipartFile file) {
        ImportParams importParams = new ImportParams();
//        importParams.setTitleRows(1);
//        importParams.setHeadRows(1);
        try {
            //读取excel
            List<ImportDTO> resultList = ExcelImportUtil.importExcel(file.getInputStream(), ImportDTO.class, importParams);
            clueMapper.batchImport(resultList);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //导出excel文件(无keyword)
    @Override
    public void exportExcel(Map<String, Object> param, HttpServletResponse response) {
        try {
            //先查询需要导出的线索数据
            List<Clue> res = clueMapper.getClueForEmport();
//            ExportDTO exportDTO = new ExportDTO();
//            List<ExportDTO> lists = BeanUtils.copyProperties(res,exportDTO);
            //设置信息头，告诉浏览器内容为excel类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //设置下载名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("线索客户表.xls", StandardCharsets.UTF_8.name()));
            //字节流输出
            ServletOutputStream out = response.getOutputStream();
            //设置excel参数
            ExportParams params = new ExportParams();
            //设置sheet名名称
            params.setSheetName("线索列表");
            //设置标题
            params.setTitle("线索信息表");
            Workbook workbook = ExcelExportUtil.exportExcel(params, Clue.class, res);
            //下面语句是导入到本地的某个excel文档里
            //FileOutputStream fos = new FileOutputStream("/Users/jarvis/Downloads/export.xlsx");
            workbook.write(out);
            //fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //导出excel文件(有keyword)
    @Override
    public void exportExcelByKeyword(String keyword, Map<String, Object> param, HttpServletResponse response) {
        try {
            //先查询需要导出的线索数据
            List<Clue> res = clueMapper.getClueForEmportByKeyword(keyword);
            //设置信息头，告诉浏览器内容为excel类型
            response.setHeader("content-Type", "application/vnd.ms-excel");
            //设置下载名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("线索客户表.xls", StandardCharsets.UTF_8.name()));
            //字节流输出
            ServletOutputStream out = response.getOutputStream();
            //设置excel参数
            ExportParams params = new ExportParams();
            //设置sheet名名称
            params.setSheetName("线索列表");
            //设置标题
            params.setTitle("线索信息表");
            Workbook workbook = ExcelExportUtil.exportExcel(params, Clue.class, res);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
