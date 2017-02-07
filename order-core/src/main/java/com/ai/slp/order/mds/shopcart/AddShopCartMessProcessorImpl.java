package com.ai.slp.order.mds.shopcart;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 添加购物车消息处理
 */
public class AddShopCartMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(AddShopCartMessProcessorImpl.class);

    IShopCartBusiSV shopCartBusiSV;

    public AddShopCartMessProcessorImpl(IShopCartBusiSV shopCartBusiSV){
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
        CartProd cartProd = JSON.parseObject(content,CartProd.class);
        if (cartProd==null)
            return;
        try {
			this.shopCartBusiSV.addCartProd(cartProd);
        } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}
    }

    public void setCartProdAtomSV(IShopCartBusiSV shopCartBusiSV) {
        this.shopCartBusiSV = shopCartBusiSV;
    }
}
