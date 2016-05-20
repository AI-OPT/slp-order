package com.ai.slp.order.util;

import java.util.Random;

import com.ai.opt.sdk.sequence.util.SeqUtil;

public final class SequenceUtil {

    /**
     * 属性定义标识序列
     */
    private static final String ORD_ORDER$ORDER_ID$SEQ = "ORD_ORDER$ORDER_ID$SEQ";

    private static final String ORD_OD_PROD$PROD_DETAL_ID$SEQ = "ORD_OD_PROD$PROD_DETAL_ID$SEQ";

    private static final String ORD_OD_STATE_CHG$STATE_CHG_ID$SEQ = "ORD_OD_STATE_CHG$STATE_CHG_ID$SEQ";

    private static final String ORD_OD_PROD_EXTEND$PROD_DETAL_EXTEND_ID$SEQ = "ORD_OD_PROD_EXTEND$PROD_DETAL_EXTEND_ID$SEQ";

    public static Long createOrderId() {
        String seq = SeqUtil.getNewId(ORD_ORDER$ORDER_ID$SEQ, 15);
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String orderId = seq + rannum;
        return Long.valueOf(orderId);
    }

    public static Long createProdDetailId() {
        Long newId = SeqUtil.getNewId(ORD_OD_PROD$PROD_DETAL_ID$SEQ);
        return Long.valueOf(newId);
    }

    public static Long createStateChgId() {
        Long newId = SeqUtil.getNewId(ORD_OD_STATE_CHG$STATE_CHG_ID$SEQ);
        return Long.valueOf(newId);
    }

    public static Long createProdDetailExtendId() {
        Long newId = SeqUtil.getNewId(ORD_OD_PROD_EXTEND$PROD_DETAL_EXTEND_ID$SEQ);
        return Long.valueOf(newId);
    }

}
