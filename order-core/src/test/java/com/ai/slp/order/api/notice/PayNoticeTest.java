package com.ai.slp.order.api.notice;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.notice.interfaces.IPayNoticeSV;
import com.ai.slp.order.util.Key;
import com.ai.slp.order.util.KeyType;
import com.ai.slp.order.util.NoticeUtil;
import com.alibaba.fastjson.JSON;
import com.changhong.upp.business.entity.upp_103_001_01.GrpBody;
import com.changhong.upp.business.entity.upp_103_001_01.GrpHdr;
import com.changhong.upp.business.entity.upp_103_001_01.RespInfo;
import com.changhong.upp.business.type.TranType;
import com.changhong.upp.crypto.rsa.RSACoder;
import com.changhong.upp.util.XBConvertor;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class PayNoticeTest {
	@Autowired
    private IPayNoticeSV iPayNoticeSV;
	@Resource(name="key")
	 private Key key;
	@Test
    public void testPayNoticeSV(){
		GrpHdr hdr = new GrpHdr();
		String merNo="CO20160900000009";
		hdr.setMerNo(merNo);
		hdr.setCreDtTm(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		hdr.setTranType(TranType.PAY_NOTICE.getValue());
		// 消息体
		GrpBody body = new GrpBody();
		body.setMerOrderId("4232342");
		body.setOrderAmt("412432");
		body.setPayStatus("00");
		body.setRemark("3231");
		body.setResv("4234234");
		body.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		body.setPaymentChannel("09");
		RespInfo respInfo = new RespInfo();
		respInfo.setGrpHdr(hdr);
		respInfo.setGrpBody(body);
		String data = null;
		try {
			 data = XBConvertor.toXml(respInfo, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 加签
		String sign = null;
		try {
			sign = RSACoder.sign(key.getKey(KeyType.PRIVATE_KEY),data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 拼装报文头
		String msgHeader = NoticeUtil.initMsgHeader(merNo, TranType.PAY_NOTICE.getValue());
		BaseResponse INFO= iPayNoticeSV.getPayNotice(data, sign, msgHeader);
        System.out.println("info="+JSON.toJSONString(INFO.getResponseHeader()));
    }
}
