package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdOrderVo implements Serializable{

	private long orderId;

	/**
	 * 租户id
	 */
    private String tenantId;

    /**
     * 业务类型
     */
    private String busiCode;

    /**
     * 订单类型
     */
    private String orderType;

    private String subFlag;

    private long parentOrderId;

    private long batchNo;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 帐号ID
     */
    private long acctId;

    /**
     * 订购ID
     */
    private long subsId;

    /**
     * 供应商ID
     */
    private String supplierId;

    private String storageId;

    private String routeId;

    /**
     * 省分
     */
    private String provinceCode;

    /**
     * 地市
     */
    private String cityCode;

    /**
     * 订单状态
     */
    private String state;

    private Timestamp stateChgTime;

    private String displayFlag;

    private Timestamp displayFlagChgTime;

    /**
     * 是否需要物流
     */
    private String deliveryFlag;

    private String lockFlag;

    private Timestamp lockTime;

    private Timestamp orderTime;

    /**
     * 销售商ID
     */
    private long sellerId;

    /**
     * 订单来源
     */
    private String chlId;

    /**
     * 操作员ID
     */
    private String operId;

    private String workflowId;

    private String reasonType;

    private String reasonDesc;

    private Timestamp finishTime;

    private long origOrderId;

    /**
     * 订单简要信息
     */
    private String orderDesc;

    /**
     * 订单关键词
     */
    private String keywords;

    /**
     * 订单备注
     */
    private String remark;

    private String externalOrderId;

    private String externalSupplyId;

    private String downstreamOrderId;

    private String userType;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 是否预警
     */
    private String ifWarning;

    /**
     * 预警类型
     */
    private String warningType;

    private String cusServiceFlag;

    private String accountId;

    private String flag;

    private String tokenId;

    private String userName;

    private String userTel;

    private String pointRate;
	
}
