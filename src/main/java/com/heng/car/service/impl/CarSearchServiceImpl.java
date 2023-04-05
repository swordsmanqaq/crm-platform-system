package com.heng.car.service.impl;/**
 * @author shkstart
 * @create 2023-04-04 10:05
 */

import com.heng.base.utils.PageList;
import com.heng.car.doc.CarDoc;
import com.heng.car.query.CarSearchQuery;
import com.heng.car.repository.CarDocRepository;
import com.heng.car.service.ICarSearchService;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Auther:Jarvis
 * @Date:2023年04月2023/4/4日10:05
 * @Description:
 */
@Service
public class CarSearchServiceImpl implements ICarSearchService {

    @Autowired
    private CarDocRepository carDocRepository;

    @Override
    public PageList<CarDoc> search(CarSearchQuery carSearchQuery) {
        //创建本地的SearchQueryBuilder对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //添加筛选条件
        //到bool这一层
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //1、keyword关键字进行分词查询
        //参数非空校验
        if (!StringUtils.isEmpty(carSearchQuery.getKeyword())) {
            //进到must这一层
            List<QueryBuilder> must = boolQueryBuilder.must();
            must.add(QueryBuilders.multiMatchQuery(carSearchQuery.getKeyword(), "title", "shopName", "shopAddress", "typeName", "carInfo"));
        }

        //进到filter这一层
        List<QueryBuilder> filter = boolQueryBuilder.filter();
        //2、车辆类型
        //非空校验
        if (Objects.nonNull(carSearchQuery.getTypeId())) {
            filter.add(QueryBuilders.termQuery("typeId", carSearchQuery.getTypeId()));
        }

        //3、价格区间
        //非空校验
        if (Objects.nonNull(carSearchQuery.getMinPrice())) {
            filter.add(QueryBuilders.rangeQuery("costprice").gte(carSearchQuery.getMinPrice()));
        }
        if (Objects.nonNull(carSearchQuery.getMaxPrice())) {
            filter.add(QueryBuilders.rangeQuery("costprice").lte(carSearchQuery.getMaxPrice()));
        }

        //4、车龄
        //非空校验
        if (Objects.nonNull(carSearchQuery.getCarAge())) {
            Date date = DateUtils.addYears(new Date(), 0 - carSearchQuery.getCarAge());
            Long time = date.getTime();
            //判断以内还是以上的类型
            if (carSearchQuery.getCarAgeType() == 0) {
                filter.add(QueryBuilders.rangeQuery("reigstertime").gte(time));
            } else {
                filter.add(QueryBuilders.rangeQuery("reigstertime").lt(time));
            }
        }

        //5、是否超值 急售 准新车 可迁全国
        //非空校验
        if (Objects.nonNull(carSearchQuery.getCosteffective())) {
            filter.add(QueryBuilders.termQuery("costeffective", carSearchQuery.getCosteffective()));
        }
        if (Objects.nonNull(carSearchQuery.getRushsale())) {
            filter.add(QueryBuilders.termQuery("rushsale", carSearchQuery.getRushsale()));
        }
        if (Objects.nonNull(carSearchQuery.getQuasinewcar())) {
            filter.add(QueryBuilders.termQuery("quasinewcar", carSearchQuery.getQuasinewcar()));
        }
        if (Objects.nonNull(carSearchQuery.getTransitivecountry())) {
            filter.add(QueryBuilders.termQuery("transitivecountry", carSearchQuery.getTransitivecountry()));
        }

        //将条件添加到query里面
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);

        //排序条件
        SortOrder sortOrder = SortOrder.ASC;
        if ("desc".equals(carSearchQuery.getSortType())) {
            sortOrder = SortOrder.DESC;
        }
        if (StringUtils.isEmpty(carSearchQuery.getSortField())) {
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("onsaletime").order(sortOrder));
        } else {
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(carSearchQuery.getSortField()).order(sortOrder));
        }

        //分页条件
        nativeSearchQueryBuilder.withPageable(PageRequest.of((int) (carSearchQuery.getCurrentPage() - 1), Math.toIntExact(carSearchQuery.getPageSize())));

        //通过Repository中的search方法进行查询
        Page<CarDoc> search = carDocRepository.search(nativeSearchQueryBuilder.build());
        List<CarDoc> content = search.getContent();
        long elements = search.getTotalElements();
        return new PageList<>(elements, content);

    }
}
