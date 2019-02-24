package com.mod.loan.mapper;

import org.apache.ibatis.annotations.Param;

import com.mod.loan.common.mapper.MyBaseMapper;
import com.mod.loan.model.OrderPay;

public interface OrderPayMapper extends MyBaseMapper<OrderPay> {

	int countOrderPaySuccessRecord(@Param("orderId") Long orderId);
}