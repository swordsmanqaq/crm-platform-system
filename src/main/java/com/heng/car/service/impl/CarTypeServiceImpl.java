package com.heng.car.service.impl;

import com.heng.base.constants.BaseConstants;
import com.heng.base.service.impl.BaseServiceImpl;
import com.heng.car.domain.CarType;
import com.heng.car.mapper.CarTypeMapper;
import com.heng.car.service.ICarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
public class CarTypeServiceImpl extends BaseServiceImpl<CarType> implements ICarTypeService {

    @Autowired
    private CarTypeMapper carTypeMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void insert(CarType carType) {
        super.insert(carType);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS_BACKUP);
    }

    @Override
    @Transactional
    public void remove(Serializable id) {
        super.remove(id);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS_BACKUP);
    }

    @Override
    @Transactional
    public void patchRemove(List<Long> ids) {
        super.patchRemove(ids);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS_BACKUP);
    }

    @Override
    @Transactional
    public void update(CarType carType) {
        super.update(carType);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS);
        redisTemplate.delete(BaseConstants.CARTYPE_TREE_IN_REDIS_BACKUP);
    }

    @Override
    @Transactional
    public List<CarType> getTree(Long pid) {

        Object redis = getRedisData();

        if (Objects.isNull(redis)) {
            System.out.println("redis无数据");
            //互斥锁
            synchronized (CarTypeServiceImpl.class) {
                redis = getRedisData();
                if (Objects.nonNull(redis)){
                    return (List<CarType>) redis;
                }

                List<CarType> treeLoop = getTreeLoop(pid);
                if (treeLoop == null) {
                    treeLoop = new ArrayList<>();
                }
                //设置缓存数据
                setRedisData(treeLoop);
                return treeLoop;
            }
        } else {
            System.out.println("redis有数据");
            return (List<CarType>) redis;
        }

    }

    /**
     * 获取缓存数据
     * @return
     */
    private Object getRedisData(){
        Object redis = redisTemplate.opsForValue().get(BaseConstants.CARTYPE_TREE_IN_REDIS);
        if (Objects.nonNull(redis)){
            return redis;
        }
        Object back = redisTemplate.opsForValue().get(BaseConstants.CARTYPE_TREE_IN_REDIS_BACKUP);

        return back;
    }

    /**
     * 设置缓存数据
     * @param list
     */
    private void setRedisData(List<CarType> list){
        redisTemplate.opsForValue().set(BaseConstants.CARTYPE_TREE_IN_REDIS, list);
        redisTemplate.opsForValue().set(BaseConstants.CARTYPE_TREE_IN_REDIS_BACKUP, list);
    }

    /**
     * 循环
     * @return
     */
    private List<CarType> getTreeLoop(Long pid) {
        //1.查询所有的车辆信息
        List<CarType> carTypeList = carTypeMapper.loadAll();

        Map<Long, CarType> map = new HashMap<>();
        //使用id作为key
        for (CarType carType : carTypeList) {
            map.put(carType.getId(), carType);
        }

        List<CarType> list = new ArrayList<>();
        for (CarType carType : carTypeList) {
            if (carType.getPid().equals(pid)) {
                //存放一级类型
                list.add(carType);
            } else {
                /*for (CarType parent : carTypeList){
                    if (parent.getId() == carType.getPid()){
                        parent.getChildren().add(carType);
                        break;
                    }
                }*/
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
    private List<CarType> getTreeRecursion(Long pid) {
        List<CarType> parents = carTypeMapper.loadByPid(pid);

        if (parents == null || parents.size() == 0) {
            return null;
        }
        for (CarType carType : parents) {
            List<CarType> children = getTreeRecursion(carType.getId());
            carType.setChildren(children);
        }
        return parents;
    }
}
