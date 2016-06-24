package com.ai.slp.order.dts.main;

import com.ai.opt.sdk.dts.constants.DTSConstants;
import com.ai.opt.sdk.dts.main.DTSMain;
import com.ai.slp.order.constants.OrdersConstants;

public class SlpOrderDtsMain {
    public static void main(String[] args) {
        System.setProperty(DTSConstants.OPT_SCHEDULER_NAME, OrdersConstants.DTS_SCHEDULE_NAME);
        DTSMain.main(args);
        System.out.println("OK");
    }
}
