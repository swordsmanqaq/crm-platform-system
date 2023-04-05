package com.heng.car.query;/**
 * @author shkstart
 * @create 2023-04-04 09:44
 */

import com.heng.base.query.BaseQuery;
import lombok.Data;

import java.math.BigDecimal;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/4日09:44
 *@Description:
 */
@Data
public class CarSearchQuery extends BaseQuery {

    //车辆类型Id
    private Integer typeId;

    private Double minPrice;

    private Double maxPrice;
    //车龄
    private Integer carAge;
    // 0是以内  1是以上
    private Integer carAgeType;
    //是否超值
    private Integer costeffective;
    //急售
    private Integer rushsale;
    //准新车
    private Integer quasinewcar;
    //可迁全国
    private Integer transitivecountry;

    //排序字段
    private String sortField;
    //排序类型 ASC DESC
    private String sortType;

}
