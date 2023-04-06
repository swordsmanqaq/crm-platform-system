package com.heng.car.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.utils.BaiduAuditUtils;
import com.heng.base.utils.VelocityUtils;
import com.heng.car.doc.CarDoc;
import com.heng.car.domain.Car;
import com.heng.car.domain.CarDetail;
import com.heng.car.domain.CarOnlineAuditLog;
import com.heng.car.domain.CarType;
import com.heng.car.dto.CarAuditDTO;
import com.heng.car.mapper.CarDetailMapper;
import com.heng.car.mapper.CarMapper;
import com.heng.car.mapper.CarOnlineAuditLogMapper;
import com.heng.car.repository.CarDocRepository;
import com.heng.car.service.ICarService;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.org.domain.Employee;
import com.heng.org.domain.Shop;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private CarDocRepository carDocRepository;

    @Value("${jarvis.carDetail.htmlPath}")
    private String htmlPath;

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

        //新增车辆信息
        if (car.getId() != null) {
            if (car.getIsnew() == 1) {
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
        if (Objects.nonNull(carDetail1)) {
            //更新
            carDetail1.setInfo(car.getCarDetail().getInfo());
            carDetail1.setCartitle(car.getTitle());
            carDetailMapper.update(carDetail1);
        } else {
            carDetail.setCarId(car.getId());
            carDetail.setInfo(car.getCarDetail().getInfo());
            carDetail.setCartitle(car.getTitle());
            carDetailMapper.insert(carDetail);
        }

    }


    /**
     * 手动审核提交
     * @param carAuditDTO
     */
    @Override
    @Transactional
    public void saveAuditCommit(CarAuditDTO carAuditDTO) {
        Car car = carMapper.loadById(carAuditDTO.getId());
        car.setAuditstate(carAuditDTO.getAuditstate());
        carMapper.update(car);
    }


    /**
     * 上架
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public String saveOnsale(List<Long> ids, Employee loginUser) {
        //非空校验
        if (ids == null || ids.size() == 0) {
            throw new RuntimeException("你不选中数据，臣妾做不到啊");
        }
        //根据ids查询所有符合条件的数据
        List<Car> cars = carMapper.loadByIds(ids, BaseConstants.Car.STATE_WAIT_CAR_OFFSHELF, BaseConstants.Car.STATE_CAR_AUTID);
        if (cars == null || cars.size() == 0) {
            throw new RuntimeException("你选择的不是下架且通过审核的数据");
        }
        //修改数据库的状态
        //获取满足条件数据的id数组
        List<Long> carList = cars.stream().map(item -> item.getId()).collect(Collectors.toList());
        carMapper.updateState(carList, BaseConstants.Car.STATE_CAR_SHELF);

        //添加日志信息
        saveCarOnlineAuditLog(loginUser, carList, BaseConstants.CarLog.Car_Onsale);

        //在es中添加数据
        List<CarDoc> carDocList = creatCarDocs(cars);
        carDocRepository.saveAll(carDocList);

        //结果信息
        String msg = "共提交了%s条上架数据，成功了%s条，失败了%s条";
        msg = String.format(msg, ids.size(), cars.size(), (ids.size() - cars.size()));

        //给成功上架后的车辆信息添加静态页面
        for (CarDoc carDoc : carDocList){
            //获取模版路径
            String templatePath = CarServiceImpl.class.getClassLoader().getResource("carDetail.vm").getPath();
            //生成文件的路径
            String htmlFilePath = this.htmlPath + "/" + carDoc.getId() + ".html";
            //模版生成静态页面
            VelocityUtils.staticByTemplate(carDoc,templatePath,htmlFilePath);
        }

        //返回结果
        return msg;
    }

    /**
     * 下架
     * @param ids
     */
    @Override
    @Transactional
    public String saveOffsale(List<Long> ids, Employee loginUser) {
        //非空校验
        if (ids == null || ids.size() == 0){
            throw new RuntimeException("你不选中数据，臣妾做不到啊");
        }
        //数据库状态改变
        carMapper.updateState(ids,BaseConstants.Car.STATE_WAIT_CAR_OFFSHELF);
        //添加日志信息
        saveCarOnlineAuditLog(loginUser,ids,BaseConstants.CarLog.Car_Offsale);
        //删除es中的数据
        for (Long id :ids) {
            carDocRepository.deleteById(id);
        }
        //结果信息
        String msg = "共提交了%s条下架数据";
        msg = String.format(msg, ids.size());

        //删除静态页面
        for (Long id : ids) {
            //获取需要删除的静态文件的路径
            String htmlFilePath = this.htmlPath + "/" + id + ".html";
            //将该路径创建文件
            File file = new File(htmlFilePath);
            //参数校验
            if(file.exists()){
                file.delete();
            }
        }

        //返回结果
        return msg;
    }

    /**
     * 新增和更新的审核封装方法
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

    /**
     * 保存上下架操作日志
     * @param loginUser
     * @param carList
     * @param state
     */
    private void saveCarOnlineAuditLog(Employee loginUser, List<Long> carList, Integer state) {
        List<CarOnlineAuditLog> carOnlineAuditLogs = new ArrayList<>();
        for (Long carId : carList) {
            CarOnlineAuditLog log = new CarOnlineAuditLog();
            log.setCarId(carId);
            log.setState(state);
            log.setAuditId(loginUser.getId());
            log.setAuditTime(new Date());
            String msg = state == BaseConstants.CarLog.Car_Onsale ? "上架" : "下架";
            log.setNote(loginUser.getUsername() + "对车辆id:" + carId + "进行了" + msg);
            carOnlineAuditLogs.add(log);
        }
        carOnlineAuditLogMapper.patchInsert(carOnlineAuditLogs);
    }


    /**
     * 往es中放数据-- 创建CarDocs方法
     * @param cars
     * @return
     */
    private List<CarDoc> creatCarDocs(List<Car> cars) {
        List<CarDoc> carDocs = new ArrayList<>();
        for (Car car : cars) {
            CarDoc carDoc = new CarDoc();
            BeanUtils.copyProperties(car,carDoc);
            // 处理店铺
            Shop shop = car.getShop();
            if (Objects.nonNull(shop)){
                carDoc.setShopId(shop.getId());
                carDoc.setShopName(shop.getName());
                carDoc.setShopAddress(shop.getAddress());
            }
            // 处理车辆类型
            CarType carType = car.getType();
            if (Objects.nonNull(carType)){
                carDoc.setTypeId(carType.getId());
                carDoc.setTypeName(carType.getName());
            }
            // 处理详情-基本信息
            CarDetail carDetail = car.getCarDetail();
            if(Objects.nonNull(carDetail)){
                carDoc.setCarInfo(carDetail.getInfo());
            }
            carDocs.add(carDoc);
        }
        return carDocs;
    }

}
