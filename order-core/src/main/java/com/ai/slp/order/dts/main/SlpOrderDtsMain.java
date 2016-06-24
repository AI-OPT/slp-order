package com.ai.slp.order.dts.main;

import com.ai.opt.sdk.dts.constants.DTSConstants;
import com.ai.opt.sdk.dts.main.DTSMain;

public class SlpOrderDtsMain {
    public static void main(String[] args) {
        System.setProperty(DTSConstants.OPT_SCHEDULER_NAME, "slp-order-nopaycancel");
        DTSMain.main(args);
        System.out.println("OK");
    }
}
