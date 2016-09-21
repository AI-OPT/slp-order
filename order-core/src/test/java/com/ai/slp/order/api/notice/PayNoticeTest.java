package com.ai.slp.order.api.notice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.notice.interfaces.IPayNoticeSV;
import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class PayNoticeTest {
	@Autowired
    private IPayNoticeSV iPayNoticeSV;
	@Test
    public void testPayNoticeSV(){
		String body = "<GrpBody><MerOrderId>2522477</MerOrderId><PayTranSn>321232</PayTranSn><OrderAmt>123</OrderAmt><OrderDate></OrderDate><PayStatus></PayStatus><Remark></Remark><Resv>保留域不能为空</Resv></GrpBody>";
		String header="<GrpHdr><MerNo>10000000</MerNo><CreDtTm>20160918114034</CreDtTm><TranType>103.001.01</TranType></GrpHdr>";
		String sign="FDSFGSDFGD";
        BaseResponse info=iPayNoticeSV.getPayNotice(body, sign, header);
        System.out.println("info="+JSON.toJSONString(info.getResponseHeader().getIsSuccess()));
    }
}
