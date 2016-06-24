package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCancelBusiSV;
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

    private static final Log LOG = LogFactory.getLog(OrderCancelBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    IOrdOdProdAtomSV ordOdProdAtomSV;

    @Override
    public void orderCancel(OrdOrder ordOrder) throws BusinessException, SystemException {
        LOG.debug("开始处理订单[" + ordOrder.getOrderId() + "]关闭具体服务");
        /* 1.更新订单表中状态为“取消” */
        Timestamp sysDate = DateUtil.getSysDate();
        ordOrder.setState(OrdersConstants.OrdOrder.State.CANCEL);
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        /* 2.库存回退 */
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(ordOrder.getOrderId());
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
     * 获取订单商品费用明细
     * 
     * @param orderId
     * @return
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    private List<OrdOdProd> getOrdOdProds(Long orderId) throws BusinessException, SystemException {
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        // 添加搜索条件
        if (orderId.intValue() != 0 && orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }
        return ordOdProdAtomSV.selectByExample(example);
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

}
