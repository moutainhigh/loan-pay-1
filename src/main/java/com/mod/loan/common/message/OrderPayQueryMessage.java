package com.mod.loan.common.message;

/**
 * 放款结果队列的DTO
 */
public class OrderPayQueryMessage {

	private String payNo; // 放款流水号
	private String merchantAlias;// 商户别名
	private int times;// 查询次数
	private String payType;// 放款渠道

	public OrderPayQueryMessage() {
		super();
	}

	public OrderPayQueryMessage(String payNo, String merchantAlias) {
		super();
		this.payNo = payNo;
		this.merchantAlias = merchantAlias;
	}

	public OrderPayQueryMessage(String payNo, String merchantAlias, String payType) {
		super();
		this.payNo = payNo;
		this.merchantAlias = merchantAlias;
		this.payType = payType;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public String getMerchantAlias() {
		return merchantAlias;
	}

	public void setMerchantAlias(String merchantAlias) {
		this.merchantAlias = merchantAlias;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

}
