package com.heng.shop.controller;

import com.heng.shop.dto.CarNumberDTO;
import com.heng.shop.dto.ChangeStateDTO;
import com.heng.shop.service.IShopingCarService;
import com.heng.shop.domain.ShopingCar;
import com.heng.shop.query.ShopingCarQuery;
import com.heng.base.utils.PageList;
import com.heng.base.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopingCar" )
public class ShopingCarController {
    @Autowired
    public IShopingCarService shopingCarService;


    /**
     * 保存和修改公用的
     * @param shopingCar 传递的实体
     * @return Ajaxresult转换结果
     */
    @PutMapping
    public AjaxResult addOrUpdate(@RequestBody ShopingCar shopingCar) {
        try {
            if ( shopingCar.getId() != null)
                shopingCarService.update(shopingCar);
            else
                shopingCarService.insert(shopingCar);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @DeleteMapping("/{id}" )
    public AjaxResult remove(@PathVariable("id" ) Long id) {
        try {
            shopingCarService.remove(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
    */
    @PatchMapping
    public AjaxResult patchRemove(@RequestBody List<Long> ids) {
        try {
            shopingCarService.patchRemove(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败！" + e.getMessage());
        }
    }

    /**
    * 根据Id获取用户
    * @param id
    * @return
    */
    @GetMapping("/{id}" )
    public AjaxResult loadById(@PathVariable("id" ) Long id) {
        try {
            ShopingCar shopingCar =shopingCarService.loadById(id);
            return AjaxResult.me().setResultObj(shopingCar);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取一个失败！" + e.getMessage());
        }
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @GetMapping
    public AjaxResult loadAll() {

        try {
            List< ShopingCar> list = shopingCarService.loadAll();
            return AjaxResult.me().setResultObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取所有失败！" + e.getMessage());
        }
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @PostMapping
    public AjaxResult pageList(@RequestBody ShopingCarQuery query) {
        try {
            PageList<ShopingCar> pageList = shopingCarService.pageList(query);
            return AjaxResult.me().setResultObj(pageList);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }


    /**
     * 添加购物车
     * @param shopingCar
     * @return
     */
    @PostMapping("/shop_car")
    public AjaxResult addShppingCar(@RequestBody ShopingCar shopingCar) {
        try {
            shopingCarService.addShppingCar(shopingCar);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取分页数据失败！" + e.getMessage());
        }
    }

    /**
     * 获取购物车信息总条数
     * @return
     */
    @GetMapping("/total/{loginId}")
    public AjaxResult getShopingCarsTotal(@PathVariable("loginId") Long loginId) {
        try {
             Long total = shopingCarService.getShopingCarsTotal(loginId);
            return AjaxResult.me().setResultObj(total);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取总条数失败" + e.getMessage());
        }
    }

    /**
     * 获取用户的购物车所有信息
     * @param loginId
     * @return
     */
    @GetMapping("/shopcarbyuser/{loginId}")
    public AjaxResult getShopingCarsByUser(@PathVariable("loginId") Long loginId) {
        try {
            List<ShopingCar> list = shopingCarService.getShopingCarsByUser(loginId);
            return AjaxResult.me().setResultObj(list);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("获取信息失败" + e.getMessage());
        }
    }

    /**
     * 清空购物车
     * @param loginId
     * @return
     */
    @PatchMapping("/empty/{loginId}")
    public AjaxResult emptyShoppingCar(@PathVariable("loginId") Long loginId) {
        try {
            shopingCarService.emptyShoppingCar(loginId);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("清空购物车失败" + e.getMessage());
        }
    }

    /**
     * 车辆数量增加
     * @param dto
     * @return
     */
    @PostMapping("/increase")
    public AjaxResult increase(@RequestBody CarNumberDTO dto) {
        try {
            shopingCarService.increase(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("数量增加失败" + e.getMessage());
        }
    }

    /**
     * 车辆数量减少
     * @param dto
     * @return
     */
    @PostMapping("/decrease")
    public AjaxResult decrease(@RequestBody CarNumberDTO dto) {
        try {
            shopingCarService.decrease(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("数量减少失败" + e.getMessage());
        }
    }


    /**
     * 选中状态
     * @param dto
     * @return
     */
    @PostMapping("/changestate")
    public AjaxResult changeCarState(@RequestBody ChangeStateDTO dto) {
        try {
            shopingCarService.changeCarState(dto);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("数量减少失败" + e.getMessage());
        }
    }

}
