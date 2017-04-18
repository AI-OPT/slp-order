package com.ai.slp.order.api.shopcart.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.*;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
import com.alibaba.dubbo.config.annotation.Service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by jackieliu on 16/5/16.
 */
@Service(validation = "true")
@Component
public class IShopCartSVImpl implements IShopCartSV {
	
	private static Logger logger = LoggerFactory.getLogger(IShopCartSVImpl.class);
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
        	ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
            String cartUserId = IPassMcsUtils.genShopCartUserId(userInfo.getTenantId(),userInfo.getUserId());
            //查询出缓存中购物车所有商品信息
            Map<String,String> cartProdMap = iCacheClient.hgetAll(cartUserId);
            //删除概览信息
            cartProdMap.remove(ShopCartConstants.McsParams.CART_POINTS);
            
            List<CartProdInfo> prodInfos = shopCartBusiSV.queryCartProdOfUser(userInfo.getTenantId(),
            		userInfo.getUserId(),cartProdMap);
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
	        //检查sku单品库存
	        checkSkuInfoTotal(cartProd.getTenantId(),cartProd.getSkuId(),cartProd.getBuyNum());
	        
			optRes = shopCartBusiSV.updateCartProd(cartProd,iCacheClient,cartUserId);
			optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
		}catch (BusinessException|SystemException e){
			optRes = new CartProdOptRes();
			optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
		}
		return optRes;
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
		try {
			optRes = shopCartBusiSV.deleteCartProd(multiCartProd.getTenantId(),multiCartProd.getUserId(),multiCartProd.getSkuIdList());
			optRes.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"成功"));
		}catch (BusinessException|SystemException e){
			optRes = new CartProdOptRes();
			optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
		}
		return optRes;
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
    
    
    /**
     * 检查SKU单品库存
     * @param tenantId
     * @param skuId
     * @param buyNum
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    private void checkSkuInfoTotal(String tenantId,String skuId,long buyNum){
        SkuInfoQuery skuInfoQuery = new SkuInfoQuery();
        skuInfoQuery.setTenantId(tenantId);
        skuInfoQuery.setSkuId(skuId);
        IProductServerSV productServerSV = DubboConsumerFactory.getService(IProductServerSV.class);
        ProductSkuInfo skuInfo = productServerSV.queryProductSkuById4ShopCart(skuInfoQuery);
        
        if (skuInfo==null || skuInfo.getUsableNum()<=0){
            throw new BusinessException("","商品已售罄或下架");
        }
        if ( buyNum>skuInfo.getUsableNum()){
            logger.warn("单品库存{},检查库存{}",skuInfo.getUsableNum(),buyNum);
            throw new BusinessException("","商品库存不足["+buyNum+"]");
        }

    }
}
