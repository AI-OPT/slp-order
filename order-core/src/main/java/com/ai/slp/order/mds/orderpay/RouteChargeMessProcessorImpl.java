package com.ai.slp.order.mds.orderpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.vo.RouteServResVo;
import com.ai.slp.route.api.server.interfaces.IRouteServer;
import com.ai.slp.route.api.server.params.IRouteServerRequest;
import com.ai.slp.route.api.server.params.RouteServerResponse;
import com.alibaba.fastjson.JSON;

/**
 * 充值消息处理 Date: 2016年6月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class RouteChargeMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(RouteChargeMessProcessorImpl.class);

    private IOrdOrderAtomSV ordOrderAtomSV;

    public RouteChargeMessProcessorImpl(IOrdOrderAtomSV ordOrderAtomSV) {
        this.ordOrderAtomSV = ordOrderAtomSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        logger.info("开始处理充值消息.........");
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}", message.getTopic(), new String(
                message.getKey(), "UTF-8"), content);

        IRouteServer iRouteServer = DubboConsumerFactory.getService("iRouteServer");
        // 转换对象
        IRouteServerRequest request = JSON.parseObject(content, IRouteServerRequest.class);
        if (request == null)
            return;
        logger.info("调用充值服务.........");
        RouteServerResponse response = iRouteServer.callServerByRouteId(request);
        String responseData = response.getResponseData();
        if (StringUtil.isBlank(responseData)) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "充值路由返回参数为空");
        }
        logger.info("更新订单表.........");
        RouteServResVo routeServResVo = JSON.parseObject(responseData, RouteServResVo.class);
        String orderId = routeServResVo.getOrderId();
        OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId("SLP", Long.valueOf(orderId));
        ordOrder.setExternalOrderId(routeServResVo.getCoopOrderId());
        ordOrder.setExternalSupplyId(routeServResVo.getCoSysId());// 725版本实现该功能
        ordOrder.setState(routeServResVo.getCoopOrderStatus());
        ordOrder.setStateChgTime(DateUtil.getSysDate());
        ordOrderAtomSV.updateById(ordOrder);
    }

}
