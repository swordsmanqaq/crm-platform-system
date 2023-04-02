package com.heng.car.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.BaiduAuditUtils;
import com.heng.car.domain.Car;
import com.heng.car.domain.CarDetail;
import com.heng.car.domain.CarOnlineAuditLog;
import com.heng.car.dto.CarAuditDTO;
import com.heng.car.mapper.CarDetailMapper;
import com.heng.car.mapper.CarMapper;
import com.heng.car.mapper.CarOnlineAuditLogMapper;
import com.heng.car.service.ICarService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
@Service
public class CarServiceImpl extends BaseServiceImpl<Car> implements ICarService {


    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarOnlineAuditLogMapper carOnlineAuditLogMapper;

    @Autowired
    private CarDetailMapper carDetailMapper;

    @Override
    @Transactional
    public void saveCars(Car car, Employee loginUser) {
        //设置car里面的店铺id 和 name
        car.setShopId(loginUser.getShop().getId());
        car.setShopname(loginUser.getShop().getName());
        car.setSellerId(loginUser.getId());
        car.setCreatetime(new Date());
        car.setState(BaseConstants.Car.STATE_WAIT_CAR_OFFSHELF);

        CarOnlineAuditLog carOnlineAuditLog = insertAndUpdateAudit(car);
//        //车辆售卖需要做审核
//        List<String> text = new ArrayList<>();
//        //需要审核的信息
//        text.add(car.getTitle());
//        text.add(car.getCarDetail().getInfo());
//        List<String> cover = new ArrayList<>();
//        cover.add(car.getCover());
//        Map<String, Object> map = BaiduAuditUtils.contextCensor(text, cover);
//        Boolean success = (Boolean) map.get("success");
//        CarOnlineAuditLog carOnlineAuditLog = new CarOnlineAuditLog();
//        if (success){
//            car.setAuditstate(BaseConstants.Car.STATE_CAR_AUTID);
//            //审核日志
//            carOnlineAuditLog.setAuditTime(new Date());
//            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_NORMAL);
//            carOnlineAuditLog.setNote("系统审核成功");
//        }else {
//            car.setAuditstate(BaseConstants.Car.STATE_WAIT_CAR_NOAUTID);
//            //审核日志
//            carOnlineAuditLog.setAuditTime(new Date());
//            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_WAIT_AUTID);
//            carOnlineAuditLog.setNote(map.get("message").toString());
//        }
        //新增车辆信息
        if (car.getId() != null) {
            if (car.getIsnew() == 1){
                car.setReigstertime(null);
                car.setMileage(null);
            }
            car.setTypeId(car.getType().getId());
            carMapper.update(car);
        } else {
            System.out.println(car);
            carMapper.insert(car);
        }

        //添加车辆日志信息
        carOnlineAuditLog.setCarId(car.getId());
        carOnlineAuditLogMapper.insert(carOnlineAuditLog);

        //车辆细节表
        CarDetail carDetail = new CarDetail();
        CarDetail carDetail1 = carDetailMapper.loadByCarId(car.getId());
        if (Objects.nonNull(carDetail1)){
            //更新
            carDetail1.setInfo(car.getCarDetail().getInfo());
            carDetail1.setCartitle(car.getTitle());
            carDetailMapper.update(carDetail1);
        }else {
            carDetail.setCarId(car.getId());
            carDetail.setInfo(car.getCarDetail().getInfo());
            carDetail.setCartitle(car.getTitle());
            carDetailMapper.insert(carDetail);
        }

    }

    /**
     * 手动审核提交
     *
     * @param carAuditDTO
     */
    @Override
    public void saveAuditCommit(CarAuditDTO carAuditDTO) {
        Car car = carMapper.loadById(carAuditDTO.getId());
        car.setAuditstate(carAuditDTO.getAuditstate());
        carMapper.update(car);
    }

    /**
     * 新增和更新的审核封装方法
     *
     * @param car
     */
    public CarOnlineAuditLog insertAndUpdateAudit(Car car) {
        //车辆售卖需要做审核
        List<String> text = new ArrayList<>();
        //需要审核的信息
        text.add(car.getTitle());
        text.add(car.getCarDetail().getInfo());
        List<String> cover = new ArrayList<>();
        cover.add(car.getCover());
        Map<String, Object> map = BaiduAuditUtils.contextCensor(text, cover);
        Boolean success = (Boolean) map.get("success");
        CarOnlineAuditLog carOnlineAuditLog = new CarOnlineAuditLog();
        if (success) {
            car.setAuditstate(BaseConstants.Car.STATE_CAR_AUTID);
            //审核日志
            carOnlineAuditLog.setAuditTime(new Date());
            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_NORMAL);
            carOnlineAuditLog.setNote("系统审核成功");
        } else {
            car.setAuditstate(BaseConstants.Car.STATE_WAIT_CAR_NOAUTID);
            //审核日志
            carOnlineAuditLog.setAuditTime(new Date());
            carOnlineAuditLog.setState(BaseConstants.CarAudit.STATE_WAIT_AUTID);
            carOnlineAuditLog.setNote(map.get("message").toString());
        }
        return carOnlineAuditLog;
    }
}
