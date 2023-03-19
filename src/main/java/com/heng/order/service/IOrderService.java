package com.heng.order.service;

import com.heng.order.domain.Order;
import com.heng.base.service.IBaseService;
import com.heng.order.dto.PayBalanceDTO;
import com.heng.org.domain.Employee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-16
 */
public interface IOrderService extends IBaseService<Order> {

    void orderPayBalance(PayBalanceDTO dto, Employee loginUser);
}
