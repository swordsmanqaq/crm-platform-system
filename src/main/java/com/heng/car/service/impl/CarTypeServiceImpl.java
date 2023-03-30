package com.heng.car.service.impl;

import com.heng.car.domain.CarType;
import com.heng.car.mapper.CarTypeMapper;
import com.heng.car.service.ICarTypeService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.TypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-29
 */
@Service
public class CarTypeServiceImpl extends BaseServiceImpl<CarType> implements ICarTypeService {

    @Autowired
    private CarTypeMapper carTypeMapper;

    @Override
    public List<CarType> getTree(Long pid) {
        List<CarType> parents = carTypeMapper.loadByPid(pid);

        if (parents == null || parents.size() == 0){
            return null;
        }
        for (CarType carType : parents) {
            List<CarType> children = getTree(carType.getId());
            carType.setChildren(children);
        }
        return parents;
    }
}
