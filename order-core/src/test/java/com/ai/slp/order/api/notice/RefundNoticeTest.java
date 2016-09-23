package com.ai.slp.order.api.notice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.notice.interfaces.IRefundNoticeSV;
import com.ai.slp.order.util.NoticeUtil;
import com.alibaba.fastjson.JSON;
import com.upp.docking.enums.TranType;
import com.ylink.upp.base.oxm.util.OxmHandler;
import com.ylink.upp.oxm.entity.upp_803_001_01.GrpBody;
import com.ylink.upp.oxm.entity.upp_803_001_01.GrpHdr;
import com.ylink.upp.oxm.entity.upp_803_001_01.RespInfo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class RefundNoticeTest {
	@Autowired
    private IRefundNoticeSV iRefundNoticeSV;
	@Autowired
	private OxmHandler oxmHandler;
	@Test
    public void testRefundNotice(){
		GrpHdr hdr = new GrpHdr();
		String merNo="CO20160900000009";
		hdr.setMerNo(merNo);
		hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		hdr.setTranType(TranType.PAY_NOTICE.getValue());
		// 消息体
		GrpBody body = new GrpBody();
		body.setMerRefundSn("4243");
		body.setRefundAmt(21l);
		body.setRefundDate(new Date().getTime());
		RespInfo respInfo = new RespInfo();
		respInfo.setGrpHdr(hdr);
		respInfo.setGrpBody(body);
		// 发送消息
		String xmlMsg = null;
		try {
			xmlMsg = oxmHandler.marshal(respInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 加签
		String sign = null;
		try {
			sign = NoticeUtil.sign(xmlMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拼装报文头
		String msgHeader = NoticeUtil.initMsgHeader(merNo, TranType.PAY_NOTICE.getValue());
		BaseResponse INFO= iRefundNoticeSV.getRefundNotice(xmlMsg, sign, msgHeader);
        System.out.println("info="+JSON.toJSONString(INFO.getResponseHeader()));
    }
	
}