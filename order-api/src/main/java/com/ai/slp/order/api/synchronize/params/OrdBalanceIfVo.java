package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdBalanceIfVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 支付流水
	 */
	private long balacneIfId;

	private String tenantId;

	private long orderId;

	private String payStyle;

	/**
	 * 已支付金额
	 */
	private long payFee;

	private String paySystemId;

	private String externalId;

	private Timestamp createTime;

	private String remark;

}
