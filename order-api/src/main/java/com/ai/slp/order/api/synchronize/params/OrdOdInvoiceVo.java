package com.ai.slp.order.api.synchronize.params;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdOdInvoiceVo implements Serializable{

	private long orderId;

    private String tenantId;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 发票内容类型
     */
    private String invoiceContent;

    /**
     * 发票状态
     */
    private String invoiceStatus;

    private String invoiceId;

    private String invoiceNum;

    /**
     * 发票种类
     */
    private String invoiceKind;

    private Timestamp invoiceTime;

    /**
     * 纳税人识别号
     */
    private String buyerTaxpayerNumber;

    /**
     * 购货方开户行代码
     */
    private String buyerBankCode;

    /**
     * 购货方开户行名称
     */
    private String buyerBankName;

    /**
     * 购货方开户行帐号
     */
    private String buyerBankAccount;
}
