package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;

public class OrdOdLogisticVo implements Serializable{

	private long logisticsId;

    private String tenantId;

    private long orderId;

    /**
     * 配送方式
     */
    private String logisticsType;

    private String expressOddNumber;

    /**
     * 到件方公司
     */
    private String contactCompany;

    /**
     * 收件人姓名
     */
    private String contactName;

    /**
     * 收件人电话
     */
    private String contactTel;

    /**
     * 收件人邮箱
     */
    private String contactEmail;

    /**
     * 收件人省分
     */
    private String provinceCode;

    /**
     * 收件人地市
     */
    private String cityCode;

    /**
     * 收件人区县
     */
    private String countyCode;

    /**
     * 收件人邮编
     */
    private String postcode;

    /**
     * 收件人末级区域
     */
    private String areaCode;

    /**
     * 收件人详细地址
     */
    private String address;

    /**
     * 物流公司ID
     */
    private String expressId;

    /**
     * 快递自提点编码
     */
    private String expressSelfId;

    /**
     * 配送时间段
     */
    private String logisticsTimeId;

    /**
     * 附加信息
     */
    private String remark;
}
