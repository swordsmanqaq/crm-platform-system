package com.heng.car.repository;

import com.heng.car.doc.CarDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author shkstart
 * @create 2023-04-03 15:51
 */

@Repository
public interface CarDocRepository extends ElasticsearchCrudRepository<CarDoc,Long> {
}
