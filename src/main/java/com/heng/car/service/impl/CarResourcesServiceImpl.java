package com.heng.car.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.BaiduAuditUtils;
import com.heng.car.domain.CarOnlineAuditLog;
import com.heng.car.domain.CarResources;
import com.heng.car.mapper.CarOnlineAuditLogMapper;
import com.heng.car.mapper.CarResourcesMapper;
import com.heng.car.service.ICarResourcesService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
@Service
public class CarResourcesServiceImpl extends BaseServiceImpl<CarResources> implements ICarResourcesService {

    @Autowired
    private CarResourcesMapper carResourcesMapper;

    @Autowired
    private CarOnlineAuditLogMapper carOnlineAuditLogMapper;

    @Override
    @Transactional
    public void saveCarResource(CarResources carResources) {
        CarResources carResource = carResourcesMapper.loadByCarId(carResources.getCarId());
        List<String> text = new ArrayList<>();
        List<String> resources = new ArrayList<>();
        resources.add(carResources.getImg());
        resources.add(carResources.getViceimg());
        resources.add(carResources.getMoreimg());
        Map<String, Object> map = BaiduAuditUtils.contextCensor(text, resources);
        Boolean success = (Boolean) map.get("success");
        CarOnlineAuditLog carOnlineAuditLog = new CarOnlineAuditLog();
        if (success){
            //审核日志
            carOnlineAuditLog.setAuditTime(new Date());
            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_NORMAL);
            carOnlineAuditLog.setNote("系统审核成功");
            if (Objects.isNull(carResource)){
                carResourcesMapper.insert(carResources);
            }else {
                carResourcesMapper.updateByCarId(carResources);
            }
        }else {
            carOnlineAuditLog.setAuditTime(new Date());
            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_WAIT_AUTID);
            carOnlineAuditLog.setNote(map.get("message").toString());
        }
        //添加车辆日志信息
        carOnlineAuditLog.setCarId(carResources.getId());
        carOnlineAuditLogMapper.insert(carOnlineAuditLog);

    }

    @Override
    public CarResources loadByCarId(Long carId) {
        return carResourcesMapper.loadByCarId(carId);
    }
}
