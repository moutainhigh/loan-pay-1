package com.mod.loan.service;

import com.mod.loan.common.mapper.BaseService;
import com.mod.loan.model.Order;
import com.mod.loan.model.OrderPay;

public interface OrderService extends BaseService<Order, Long> {

	void updatePayInfo(Order order, OrderPay orderPay);

	void updatePayCallbackInfo(Order order, OrderPay orderPay);

	/**
	 * 统计订单放款成功记录
	 * 
	 * @param orderId
	 * @return
	 */
	int countOrderPaySuccessRecord(Long orderId);

}
