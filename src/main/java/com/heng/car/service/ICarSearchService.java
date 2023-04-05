package com.heng.car.service;

import com.heng.base.service.IBaseService;
import com.heng.base.utils.PageList;
import com.heng.car.doc.CarDoc;
import com.heng.car.query.CarSearchQuery;

/**
 * @author shkstart
 * @create 2023-04-04 10:04
 */
public interface ICarSearchService {
    PageList<CarDoc> search(CarSearchQuery carSearchQuery);

}
