package com.ai.slp.order.service.business.impl;

import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 购物车消息处理
 * Created by jackieliu on 16/5/19.
 */
public class ShopCartMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(ShopCartMessProcessorImpl.class);

    IOrdOdCartProdAtomSV cartProdAtomSV;

    public ShopCartMessProcessorImpl(IOrdOdCartProdAtomSV cartProdAtomSV){
        this.cartProdAtomSV = cartProdAtomSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrdOdCartProd cartProd = JSON.parseObject(content,OrdOdCartProd.class);
        if (cartProd==null)
            return;
        //若商品数量为空或零,删除购物车中商品
        if (cartProd.getBuySum()==null || new Long(0l).equals(cartProd.getBuySum())){
            cartProdAtomSV.deleteByProdId(cartProd.getTenantId(),cartProd.getUserId(),cartProd.getSkuId());
        }else {
            OrdOdCartProd cartProd0 = cartProdAtomSV.queryByProdOfCart(cartProd.getTenantId(),cartProd.getUserId(),cartProd.getSkuId());
            //若没有添加商品,则直接添加
            if (cartProd0 ==null){
                cartProdAtomSV.installCartProd(cartProd);
            }else {
                cartProd0.setBuySum(cartProd.getBuySum());
                cartProdAtomSV.updateCartProdById(cartProd0);
            }
        }
    }

    public void setCartProdAtomSV(IOrdOdCartProdAtomSV cartProdAtomSV) {
        this.cartProdAtomSV = cartProdAtomSV;
    }
}
