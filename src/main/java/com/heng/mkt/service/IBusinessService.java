package com.heng.mkt.service;

import com.heng.mkt.domain.Business;
import com.heng.base.service.IBaseService;
import com.heng.mkt.dto.PayDepositDTO;
import com.heng.org.domain.Employee;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jarvis-Smith
 * @since 2023-03-14
 */
public interface IBusinessService extends IBaseService<Business> {


    void payDeposit(PayDepositDTO dto, Employee loginUser);
}
