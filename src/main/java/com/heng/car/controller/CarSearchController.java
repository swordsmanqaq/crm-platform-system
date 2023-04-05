package com.heng.car.controller;/**
 * @author shkstart
 * @create 2023-04-04 09:58
 */

import com.heng.base.utils.AjaxResult;
import com.heng.base.utils.PageList;
import com.heng.car.doc.CarDoc;
import com.heng.car.query.CarSearchQuery;
import com.heng.car.service.ICarSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/4日09:58
 *@Description:
 */
@RestController
@RequestMapping("/carSearch")
public class CarSearchController {

    @Autowired
    private ICarSearchService carSearchService;

    /**
     * es分页查询
     * @param carSearchQuery
     * @return
     */
    @PostMapping
    public AjaxResult carSearch(@RequestBody CarSearchQuery carSearchQuery) {
        try {
            PageList<CarDoc> carDocs = carSearchService.search(carSearchQuery);
            return AjaxResult.me().setResultObj(carDocs);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("查询失败失败" + e.getMessage());
        }

    }
}
