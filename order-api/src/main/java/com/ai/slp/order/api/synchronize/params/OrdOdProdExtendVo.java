package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;

public class OrdOdProdExtendVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private long prodDetalExtendId;

	private long prodDetalId;

	private long orderId;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 扩展信息
	 */
	private String infoJson;

	/**
	 * 批量标识 
	 */
	private String batchFlag;
}
