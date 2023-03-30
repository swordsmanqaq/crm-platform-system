package com.heng.car.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.car.domain.CarType;
import com.heng.car.mapper.CarTypeMapper;
import com.heng.car.service.ICarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
public class CarTypeServiceImpl_02 extends BaseServiceImpl<CarType> implements ICarTypeService {

    @Autowired
    private CarTypeMapper carTypeMapper;


    @Override
    @CacheEvict(cacheNames = BaseConstants.CARTYPE_TREE_IN_REDIS,key = "'ALL'")
    public void insert(CarType carType) {
        super.insert(carType);
    }

    @Override
    @CacheEvict(cacheNames = BaseConstants.CARTYPE_TREE_IN_REDIS,key = "'ALL'")
    public void remove(Serializable id) {
        super.remove(id);
    }

    @Override
    @CacheEvict(cacheNames = BaseConstants.CARTYPE_TREE_IN_REDIS,key = "'ALL'")
    public void patchRemove(List<Long> ids) {
        super.patchRemove(ids);
    }
    @Override
    @CacheEvict(cacheNames = BaseConstants.CARTYPE_TREE_IN_REDIS,key = "'ALL'")
    public void update(CarType carType) {
        super.update(carType);
    }

    @Override
    @Cacheable(cacheNames = BaseConstants.CARTYPE_TREE_IN_REDIS,key = "'ALL'")
    public List<CarType> getTree(Long pid) {
        return getTreeLoop(pid);

    }

    /**
     * 循环
     * @return
     */
    private List<CarType> getTreeLoop(Long pid){
        //1.查询所有的车辆
        List<CarType> carTypeList = carTypeMapper.loadAll();

        Map<Long, CarType> map = new HashMap<>();

        for (CarType carType :carTypeList){
            map.put(carType.getId(),carType);
        }

        List<CarType> list = new ArrayList<>();
        for (CarType carType :carTypeList){
            if (carType.getPid().equals(pid)){
                //存放一级类型
                list.add(carType);
            }else{
//                for (CarType parent : carTypeList){
//                    if (parent.getId() == carType.getPid()){
//                        parent.getChildren().add(carType);
//                        break;
//                    }
//                }
                //根据pid 获取该类型的父类型
                CarType parent = map.get(carType.getPid());
                parent.getChildren().add(carType);
            }
        }
        return list;
    }

    /**
     * 递归
     * @param pid
     * @return
     */
    private List<CarType> getTreeRecursion(Long pid){
        List<CarType> parents = carTypeMapper.loadByPid(pid);

        if (parents == null || parents.size() == 0){
            return null;
        }
        for (CarType carType : parents) {
            List<CarType> children = getTreeRecursion(carType.getId());
            carType.setChildren(children);
        }
        return parents;
    }
}
