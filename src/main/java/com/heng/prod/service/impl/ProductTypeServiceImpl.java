package com.heng.prod.service.impl;

import com.heng.prod.domain.ProductType;
import com.heng.prod.mapper.ProductMapper;
import com.heng.prod.mapper.ProductTypeMapper;
import com.heng.prod.service.IProductTypeService;
import com.heng.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements IProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;
    @Override
    public List<ProductType> getFirstProductType() {
        return productTypeMapper.getFirstProductType();

    }

    @Override
    public List<ProductType> getProdTree() {
        return productTypeMapper.getProdTree();
    }
}
