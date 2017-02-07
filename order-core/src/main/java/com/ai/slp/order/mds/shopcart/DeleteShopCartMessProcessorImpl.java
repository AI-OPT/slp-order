package com.ai.slp.order.mds.shopcart;

import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.shopcart.param.MultiCartProd;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 删除购物车消息处理
 */
public class DeleteShopCartMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(DeleteShopCartMessProcessorImpl.class);

    private IShopCartBusiSV shopCartBusiSV;

    public DeleteShopCartMessProcessorImpl(IShopCartBusiSV shopCartBusiSV){
        this.shopCartBusiSV = shopCartBusiSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        MultiCartProd multiCartProd = JSON.parseObject(content,MultiCartProd.class);
        if (multiCartProd==null)
            return;
        shopCartBusiSV.deleteCartProd(multiCartProd.getTenantId(),
        		multiCartProd.getUserId(),multiCartProd.getSkuIdList());
    }

    public void setCartProdAtomSV(IShopCartBusiSV shopCartBusiSV) {
        this.shopCartBusiSV = shopCartBusiSV;
    }
}
