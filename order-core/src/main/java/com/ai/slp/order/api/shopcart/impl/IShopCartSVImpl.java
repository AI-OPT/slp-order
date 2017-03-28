package com.ai.slp.order.api.shopcart.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.*;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.order.util.MQConfigUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jackieliu on 16/5/16.
 */
@Service(validation = "true")
@Component
public class IShopCartSVImpl implements IShopCartSV {
    @Autowired
    IShopCartBusiSV shopCartBusiSV;
    /**
     * 购物车中添加商品
     *
     * @param cartProd 购物车添加商品信息
     * @return 添加结果
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0100
     */
    @Override
    public CartProdOptRes addProd(CartProd cartProd) throws BusinessException,SystemException {
        CommonCheckUtils.checkTenantId(cartProd.getTenantId(),"");
        CartProdOptRes optRes = null;
    	boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	//ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	
    	//非消息模式下，同步调用服务
    	if(!ccsMqFlag){
    		 try {
				 	//若购买数量为空,或小于0,则设置默认为1
			        if (cartProd.getBuyNum() == null
			                || cartProd.getBuyNum()<=0)
			            cartProd.setBuyNum(1l);
			        String tenantId = cartProd.getTenantId(),userId = cartProd.getUserId();
			        
			        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
			        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
		            optRes = shopCartBusiSV.addCartProd(cartProd,iCacheClient,cartUserId);
		            ResponseHeader responseHeader = new ResponseHeader(true,
		                    ExceptCodeConstants.Special.SUCCESS, "成功");
		            optRes.setResponseHeader(responseHeader);
    	        }catch (BusinessException|SystemException e){
    	            optRes = new CartProdOptRes();
    	            optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
    	        }
    	        return optRes;
    	}else {
    		//消息模式下
    		optRes=new CartProdOptRes();
            MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_SHOPCART_ADD_TOPIC).send(JSON.toJSONString(cartProd), 0);
            ResponseHeader responseHeader = new ResponseHeader(true,
                    ExceptCodeConstants.Special.SUCCESS, "成功");
            optRes.setResponseHeader(responseHeader);
    	    return optRes;
    	}
    }

    /**
     * 查询用户的购物车信息
     *
     * @param userInfo 用户信息
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0101
     */
    @Override
    public CartProdList queryCartOfUser(UserInfo userInfo) throws BusinessException,SystemException {
        CommonCheckUtils.checkTenantId(userInfo.getTenantId(),"");
        CartProdList prodList = new CartProdList();
        try {
            List<CartProdInfo> prodInfos = shopCartBusiSV.queryCartProdOfUser(userInfo.getTenantId(),userInfo.getUserId());
            prodList.setProdInfoList(prodInfos);
            prodList.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
        }catch (BusinessException|SystemException e){
            prodList.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
        }

        return prodList;
    }

    /**
     * 调整购物车内商品数量
     *
     * @param cartProd 购物车更新商品信息
     * @return 操作结果
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0102
     */
    @Override
    public CartProdOptRes updateProdNum(CartProd cartProd) throws BusinessException,SystemException {
        CommonCheckUtils.checkTenantId(cartProd.getTenantId(),"");
        CartProdOptRes optRes = null;
        boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	//ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	//非消息模式下，同步调用服务
    	if(!ccsMqFlag){
    		try {
    			//若购买数量为空,或小于0,则设置默认为1
    	        if (cartProd.getBuyNum() == null
    	                || cartProd.getBuyNum()<=0)
    	            cartProd.setBuyNum(1l);
    	        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
    	        String cartUserId = IPassMcsUtils.genShopCartUserId(cartProd.getTenantId(),cartProd.getUserId());
    	        //购物车单个商品数量限制
    	        int skuNumLimit = getShopCartLimitNum(ShopCartConstants.CcsParams.ShopCart.SKU_NUM_LIMIT);
    	        //达到购物车单个商品数量上线
    	        if (skuNumLimit>0 && cartProd.getBuyNum()>skuNumLimit){
    	            throw new BusinessException("","此商品数量达到购物车允许最大数量,无法添加.");
    	        }
    			optRes = shopCartBusiSV.updateCartProd(cartProd,iCacheClient,cartUserId);
    			optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
    		}catch (BusinessException|SystemException e){
    			optRes = new CartProdOptRes();
    			optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
    		}
    		return optRes;
    	}else {
    		//消息模式下
    		optRes=new CartProdOptRes();
    		MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_SHOPCART_UPDATE_TOPIC).send(JSON.toJSONString(cartProd), 0);
    		optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
    		return optRes;
    	}
    }

    /**
     * 删除购物车商品,单个和批量都可以
     *
     * @param multiCartProd 购物车商品删除信息
     * @return 删除操作结果
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0103
     */
    @Override
    public CartProdOptRes deleteMultiProd(MultiCartProd multiCartProd) throws BusinessException,SystemException {
        CommonCheckUtils.checkTenantId(multiCartProd.getTenantId(),"");
        CartProdOptRes optRes = null;
        boolean ccsMqFlag=false;
    	//从配置中心获取ccsMqFlag
    	//ccsMqFlag=MQConfigUtil.getCCSMqFlag();
    	//非消息模式下，同步调用服务
    	if(!ccsMqFlag){
    		try {
    			optRes = shopCartBusiSV.deleteCartProd(multiCartProd.getTenantId(),multiCartProd.getUserId(),multiCartProd.getSkuIdList());
    			optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
    		}catch (BusinessException|SystemException e){
    			optRes = new CartProdOptRes();
    			optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
    		}
    		return optRes;
    	}else {
    		//消息模式下
    		optRes=new CartProdOptRes();
    		MDSClientFactory.getSenderClient(OrdersConstants.MDSNS.MDS_NS_SHOPCART_DELETE_TOPIC).send(JSON.toJSONString(multiCartProd), 0);
    		optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
    		return optRes;
    	}
    }

    /**
     * 查询用户的购物车概况信息
     *
     * @param userInfo 用户信息
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0104
     */
    @Override
    public CartProdOptRes queryPointsOfCart(UserInfo userInfo) throws BusinessException,SystemException {
        CommonCheckUtils.checkTenantId(userInfo.getTenantId(),"");
        CartProdOptRes optRes = null;
        try {
            optRes = shopCartBusiSV.queryCartOptions(userInfo.getTenantId(),userInfo.getUserId());
            optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
        }catch (BusinessException|SystemException e){
            optRes = new CartProdOptRes();
            optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
        }
        return optRes;
    }
    
    /**
     * 获取购物车中数量限制
     *
     * @param limitParams
     * @return -1表示没有限制
     */
    public int getShopCartLimitNum(String limitParams){
        if (StringUtils.isBlank(limitParams))
            return -1;
        String ccsParams = limitParams;
        if (!limitParams.startsWith("/"))
            ccsParams = "/"+ccsParams;
        String limitNum = null;
        try {
            limitNum = CCSClientFactory.getDefaultConfigClient().get(ccsParams);
        } catch (ConfigException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(limitNum);
    }
}
