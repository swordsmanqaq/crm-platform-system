package com.heng.car.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.BaiduAuditUtils;
import com.heng.car.domain.CarDetail;
import com.heng.car.domain.CarOnlineAuditLog;
import com.heng.car.mapper.CarDetailMapper;
import com.heng.car.mapper.CarOnlineAuditLogMapper;
import com.heng.car.service.ICarDetailService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
@Service
public class CarDetailServiceImpl extends BaseServiceImpl<CarDetail> implements ICarDetailService {

    @Autowired
    private CarDetailMapper carDetailMapper;

    @Autowired
    private CarOnlineAuditLogMapper carOnlineAuditLogMapper;

    @Override
    @Transactional
    public void saveCarReport(CarDetail carDetail, Employee loginUser) {
        //跟新数据信息
        carDetail.setDetectorname(loginUser.getUsername());
        carDetail.setDetectorId(loginUser.getId());

        List<String> text = new ArrayList<>();
        List<String> reports = new ArrayList<>();
        reports.add(carDetail.getAccidentInvestigation());
        reports.add(carDetail.getCoreComponentsInvestigation());
        reports.add(carDetail.getCommonFunctionsInvestigation());
        reports.add(carDetail.getAppearanceInspection());
        Map<String, Object> map = BaiduAuditUtils.contextCensor(text, reports);
        Boolean success = (Boolean) map.get("success");
        CarOnlineAuditLog carOnlineAuditLog = new CarOnlineAuditLog();
        if (success){
            //审核日志
            carOnlineAuditLog.setAuditTime(new Date());
            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_NORMAL);
            carOnlineAuditLog.setNote("上传检测报告照片-系统审核成功");
        }else {
            carOnlineAuditLog.setAuditTime(new Date());
            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_WAIT_AUTID);
            carOnlineAuditLog.setNote(map.get("message").toString());
        }
        carDetailMapper.updateByCarId(carDetail);

        carOnlineAuditLog.setCarId(carDetail.getCarId());
        carOnlineAuditLog.setAuditId(carDetail.getDetectorId());
        carOnlineAuditLogMapper.insert(carOnlineAuditLog);

    }

    @Override
    public CarDetail loadByCarId(Long carId) {
        return carDetailMapper.loadByCarId(carId);
    }

}
