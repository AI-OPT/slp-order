package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.search.ISearchClient;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.platform.common.api.cache.param.SysParam;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrdProdExtend;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.SearchCriteriaStructure;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.impl.search.OrderSearchImpl;
import com.ai.slp.order.service.business.interfaces.IOrderCancelBusiSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;
import com.ai.slp.order.util.InfoTranslateUtil;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumBackReq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 超过30分钟未支付订单自动关闭实现 Date: 2016年6月23日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Service
@Transactional
public class OrderCancelBusiSVImpl implements IOrderCancelBusiSV {

    private static final Logger LOG = LoggerFactory.getLogger(OrderCancelBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;
    @Autowired
    IOrdOdProdAtomSV ordOdProdAtomSV;
    @Autowired
    private IOrderFrameCoreSV orderFrameCoreSV;
    
    //订单取消
    @Override
    public void orderCancel(OrdOrder ordOrder) throws BusinessException, SystemException {
    	LOG.debug("开始处理订单[" + ordOrder.getOrderId() + "]关闭具体服务");
        /* 1.更新订单表中状态为“取消” */
        String orgState=ordOrder.getState();
        String newState=OrdersConstants.OrdOrder.State.CANCEL;
        Timestamp sysDate = DateUtil.getSysDate();
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        /* 2.写入订单状态变化轨迹表 */
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState,
                newState, OrdersConstants.OrdOdStateChg.ChgDesc.ORDER_TO_CANCEL, null, null, null, sysDate);
       
        /* 3.刷新搜索引擎数据*/
    	this.refreshStateData(ordOrder);
    	
        /* 4.库存回退 */
        List<OrdOdProd> ordOdProds =  ordOdProdAtomSV.selectByOrd(ordOrder.getTenantId(), ordOrder.getOrderId());
        if (CollectionUtil.isEmpty(ordOdProds))
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息["
                    + ordOrder.getOrderId() + "]");
        for (OrdOdProd ordOdProd : ordOdProds) {
            Map<String, Integer> storageNum = JSON.parseObject(ordOdProd.getSkuStorageId(),
                    new TypeReference<Map<String, Integer>>(){});
            this.backStorageNum(ordOdProd.getTenantId(), ordOdProd.getSkuId(), storageNum);
        }
    }

    /**
     * 库存回退
     * 
     * @param tenantId
     * @param skuId
     * @param storageNum
     * @author zhangxw
     * @ApiDocMethod
     */
    private void backStorageNum(String tenantId, String skuId, Map<String, Integer> storageNum) {
        StorageNumBackReq storageNumBackReq = new StorageNumBackReq();
        storageNumBackReq.setTenantId(tenantId);
        storageNumBackReq.setSkuId(skuId);
        storageNumBackReq.setStorageNum(storageNum);
        IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
        BaseResponse response = iStorageNumSV.backStorageNum(storageNumBackReq);
        boolean success = response.getResponseHeader().isSuccess();
        String resultMessage = response.getResponseHeader().getResultMessage();
        if (!success)
            throw new BusinessException("", "调用回退库存异常:" + skuId + "错误信息如下:" + resultMessage + "]");

    }
    
    
	/**
	 * 刷新搜索引擎状态数据
	 */
	private void refreshStateData(OrdOrder ordOrder)  {
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
  		IOrderSearch orderSearch = new OrderSearchImpl();
		List<SearchCriteria> orderSearchCriteria = SearchCriteriaStructure.
				commonConditionsByOrderId(ordOrder.getOrderId());
		Result<OrderInfo> result = orderSearch.search(orderSearchCriteria, 0, 1, null);
		List<OrderInfo> ordList = result.getContents();
		if(CollectionUtil.isEmpty(ordList)) {
			throw new BusinessException("搜索引擎无数据! 父订单id为:"+ordOrder.getParentOrderId());
		}
		OrderInfo orderInfo = ordList.get(0);
		orderInfo.setParentorderstate(ordOrder.getState());
		List<OrdProdExtend> ordextendes = orderInfo.getOrdextendes();
		for (OrdProdExtend ordProdExtend : ordextendes) {
			if(ordOrder.getOrderId()==ordProdExtend.getOrderid()) {
				ordProdExtend.setState(ordOrder.getState());
				//订单状态翻译
				SysParam sysParamState = InfoTranslateUtil.translateInfo(ordOrder.getTenantId(),
						"ORD_ORDER", "STATE",ordOrder.getState(), iCacheSV);
				ordProdExtend.setStatename(sysParamState == null ? "" : sysParamState.getColumnDesc());
			}
		}
	//	ESClientManager.getSesClient(SearchConstants.SearchNameSpace).bulkInsert(ordList);
		ISearchClient client = ESClientManager.getSesClient(SearchConstants.SearchNameSpace);
		client.bulkInsert(ordList);
		client.refresh();
	}

}
