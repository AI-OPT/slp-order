package com.ai.slp.order.mds.orderpay;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderReturnGoodBusiSV;
import com.ai.slp.order.vo.RouteServResVo;
import com.ai.slp.route.api.server.interfaces.IRouteServer;
import com.ai.slp.route.api.server.params.IRouteServerRequest;
import com.ai.slp.route.api.server.params.RouteServerResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 充值消息处理 Date: 2016年6月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class RouteChargeMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(RouteChargeMessProcessorImpl.class);

    private IOrdOrderAtomSV ordOrderAtomSV;
    
    private IOrderFrameCoreSV orderFrameCoreSV;
    
    private IOrderReturnGoodBusiSV orderReturnGoodBusiSV;

    public RouteChargeMessProcessorImpl(IOrdOrderAtomSV ordOrderAtomSV,
    		IOrderFrameCoreSV orderFrameCoreSV,IOrderReturnGoodBusiSV orderReturnGoodBusiSV) {
        this.ordOrderAtomSV = ordOrderAtomSV;
        this.orderFrameCoreSV = orderFrameCoreSV;
        this.orderReturnGoodBusiSV=orderReturnGoodBusiSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        logger.info("开始处理充值消息.........");
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        System.out.println("RouteChargeMessProcessorImpl "+content);
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}", message.getTopic(), new String(
                message.getKey(), "UTF-8"), content);

        IRouteServer iRouteServer = DubboConsumerFactory.getService("iRouteServer");
        // 转换对象
        IRouteServerRequest request = JSON.parseObject(content, IRouteServerRequest.class);
        if (request == null)
            return;
        logger.info("调用充值服务.........");
        String responseData;
        RouteServerResponse response=null;
		try {
			 response = iRouteServer.callServerByRouteId(request);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("调用充值服务失败......");
		}
		responseData = response.getResponseData();
        Timestamp sysDate = DateUtil.getSysDate();
        if (StringUtil.isBlank(responseData)) {
        	/*充值出现错误后则为充值失败*/
            logger.info("error...");
            String requestData = request.getRequestData();
            logger.info("requestData:"+requestData);
            JSONObject object = JSON.parseObject(requestData);
			String orderId=object.getString("orderId");
			logger.info("orderId:"+orderId);
			OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId("SLP", Long.valueOf(orderId));
			String orgState=ordOrder.getState();
			String newState=OrdersConstants.OrdOrder.State.CHARGE_FAILED;
			String chgDesc=OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_CHARGE_FAILED;
			ordOrder.setState(newState);
			ordOrder.setStateChgTime(sysDate);
			ordOrder.setFinishTime(sysDate);
			/* 更新子订单状态*/
			ordOrderAtomSV.updateById(ordOrder);
			/* 写入订单状态变化轨迹表 */
			orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState,
			          newState, chgDesc, null, null, null, sysDate);
			/* 如果订单状态为充值失败,则调用退款服务 */
	        try {
				orderReturnGoodBusiSV.orderReturnGoods(ordOrder, sysDate);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("调用退款服务失败......");
			}
			/*更新父订单状态*/
			OrdOrder parentOrdOrder = ordOrderAtomSV.selectByOrderId(ordOrder.getTenantId(), ordOrder.getParentOrderId());
			String parentOrgState=parentOrdOrder.getState();
			String parentNewState=OrdersConstants.OrdOrder.State.COMPLETED;
			String parentChgDesc=OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_COMPLETED;
			parentOrdOrder.setState(parentNewState);
			parentOrdOrder.setStateChgTime(sysDate);
			ordOrderAtomSV.updateById(parentOrdOrder);
			/* 写入订单状态变化轨迹表 */
			orderFrameCoreSV.ordOdStateChg(parentOrdOrder.getOrderId(), parentOrdOrder.getTenantId(), parentOrgState,
					parentNewState, parentChgDesc, null, null, null, sysDate);
			return;
        }
        
        logger.info("更新订单表.........");
        RouteServResVo routeServResVo = JSON.parseObject(responseData, RouteServResVo.class);
        String orderId = routeServResVo.getOrderId();
        OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId("SLP", Long.valueOf(orderId));
        ordOrder.setExternalOrderId(routeServResVo.getCoopOrderId());
        ordOrder.setExternalSupplyId(routeServResVo.getCoSysId());// 725版本实现该功能
        ordOrder.setState(routeServResVo.getCoopOrderStatus());
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
    }
}
