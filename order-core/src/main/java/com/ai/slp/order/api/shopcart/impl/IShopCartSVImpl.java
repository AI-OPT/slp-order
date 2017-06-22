package com.ai.slp.order.api.shopcart.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdInfo;
import com.ai.slp.order.api.shopcart.param.CartProdList;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.api.shopcart.param.MultiCartProd;
import com.ai.slp.order.api.shopcart.param.ProductSkuInfo;
import com.ai.slp.order.api.shopcart.param.UserInfo;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.constants.prod.SearchProdInfoUtils;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.manager.CacheClientManager;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.order.util.DateUtils;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.order.vo.ShopCartCachePointsVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;

/**
 * Created by jackieliu on 16/5/16.
 */
@Service(validation = "true")
@Component
public class IShopCartSVImpl implements IShopCartSV {
	
	private static Logger logger = LoggerFactory.getLogger(IShopCartSVImpl.class);
    @Autowired
    IShopCartBusiSV shopCartBusiSV;
    @Autowired
    IOrdOdCartProdAtomSV cartProdAtomSV;
    
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
	        ICacheClient iCacheClient = CacheClientManager.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
	        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
			
	        //查询用户购物车概览
	        ShopCartCachePointsVo pointsVo = queryCartPoints(iCacheClient,tenantId,userId);
	        String cartProdStr = iCacheClient.hget(cartUserId,cartProd.getSkuId());
	        OrdOdCartProd odCartProd;
	        //若已经存在
	        if (StringUtils.isNotBlank(cartProdStr)){
	            odCartProd = JSON.parseObject(cartProdStr,OrdOdCartProd.class);
	            //更新购买数量
	            odCartProd.setBuySum(odCartProd.getBuySum()+cartProd.getBuyNum());
	        }else {
	            odCartProd = new OrdOdCartProd();
	            odCartProd.setInsertTime(DateUtils.currTimeStamp());
	            odCartProd.setBuySum(cartProd.getBuyNum());
	            odCartProd.setSkuId(cartProd.getSkuId());
	            odCartProd.setTenantId(tenantId);
	            odCartProd.setUserId(userId);
	            //若是新商品,则需要将概览中加1
	            pointsVo.setProdNum(pointsVo.getProdNum()+1);
	        }
	        //购物车商品类型数量限制
	        int prodNumLimit = getShopCartLimitNum(ShopCartConstants.CcsParams.ShopCart.PROD_NUM_LIMIT);
	        //购物车单个商品数量限制
	        int skuNumLimit = getShopCartLimitNum(ShopCartConstants.CcsParams.ShopCart.SKU_NUM_LIMIT);
	        //到达商品种类上限
	        if (prodNumLimit>0 && prodNumLimit<pointsVo.getProdNum()){
	            throw new BusinessException("","购物车商品数量已经达到上限,无法添加");
	        }
	        //达到购物车单个商品数量上线
	        else if (skuNumLimit>0 && odCartProd.getBuySum()>skuNumLimit){
	            throw new BusinessException("","此商品数量达到购物车允许最大数量,无法添加.");
	        }
	        //查询商品信息
	        ProductSkuInfo skuInfo = checkSkuInfoTotal(tenantId,cartProd.getSkuId(), odCartProd.getBuySum());
	        if (StringUtils.isBlank(cartProdStr))
	            odCartProd.setSupplierId(skuInfo.getSupplierId());
	        //添加/更新商品信息
	        iCacheClient.hset(cartUserId,odCartProd.getSkuId(),JSON.toJSONString(odCartProd));
	        //更新购物车上商品总数量
	        pointsVo.setProdTotal(pointsVo.getProdTotal()+cartProd.getBuyNum());
	        //更新概览
	        iCacheClient.hset(cartUserId, ShopCartConstants.McsParams.CART_POINTS,JSON.toJSONString(pointsVo));
	        
	        optRes = shopCartBusiSV.addCartProd(odCartProd,pointsVo );
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
        	ICacheClient iCacheClient = CacheClientManager.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
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
	        ICacheClient iCacheClient = CacheClientManager.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
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
    private ProductSkuInfo checkSkuInfoTotal(String tenantId,String skuId,long buyNum){
    	//查询商品信息
    	ProductSkuInfo skuInfo = QuerySkuInfoByES(tenantId, skuId);
    	if (StringUtils.isBlank(skuInfo.getStorageGroupId())){
            throw new BusinessException("","商品已售罄或下架");
        }
    	//查询可使用量
    	Long usableNum = SearchProdInfoUtils.queryNowUsableNumOfGroup(tenantId, 
    			skuInfo.getStorageGroupId());
    	skuInfo.setUsableNum(usableNum==null?0:usableNum);
        if (skuInfo.getUsableNum()<=0){
            throw new BusinessException("","商品已售罄或下架");
        }
        if ( buyNum>skuInfo.getUsableNum()){
            logger.warn("单品库存{},检查库存{}",skuInfo.getUsableNum(),buyNum);
            throw new BusinessException("","商品库存不足["+buyNum+"]");
        }
        return skuInfo;
    }
    
    /**
     * 查询用户购物车的概览
     * @param iCacheClient
     * @param tenantId
     * @param userId
     * @return
     */
    private ShopCartCachePointsVo queryCartPoints(ICacheClient iCacheClient,String tenantId,String userId){
        //查询缓存中是否存在
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //若不存在购物车信息缓存,则建立缓存
        if (!iCacheClient.exists(cartUserId)){
            //从数据库中查询,建立缓存
            addShopCartCache(tenantId,userId,iCacheClient);
        }
        //查询概览信息
        String cartPrefix = iCacheClient.hget(cartUserId, ShopCartConstants.McsParams.CART_POINTS);
        ShopCartCachePointsVo pointsVo = JSON.parseObject(cartPrefix, ShopCartCachePointsVo.class);
        return pointsVo;
    }
    
    /**
     * 将数据库中数据加载到缓存中
     *
     * @param tenantId
     * @param userId
     */
    private void addShopCartCache(String tenantId,String userId,ICacheClient iCacheClient){
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //查询用户购物车商品列表
        List<OrdOdCartProd> cartProdList = cartProdAtomSV.queryCartProdsOfUser(tenantId,userId);
        int prodTotal = 0;
        //循环建立购物车单品缓存
        for (OrdOdCartProd cartProd:cartProdList){
            iCacheClient.hset(cartUserId, cartProd.getSkuId(),JSON.toJSONString(cartProd));
            prodTotal += cartProd.getBuySum();
        }
        //添加概览信息
        ShopCartCachePointsVo cartProdPoints = new ShopCartCachePointsVo();
        cartProdPoints.setProdNum(cartProdList.size());
        cartProdPoints.setProdTotal(prodTotal);
        iCacheClient.hset(cartUserId, ShopCartConstants.McsParams.CART_POINTS,JSON.toJSONString(cartProdPoints));
    }
    
    /**
     * 查询SKU单品信息
     * @param tenantId
     * @param skuId
     * @return
     */
    public ProductSkuInfo querySkuInfo(String tenantId,String skuId){
    	ProductSkuInfo productSkuInfo = QuerySkuInfoByES(tenantId, skuId);
    	//查询可使用量
    	Long usableNum = SearchProdInfoUtils.queryNowUsableNumOfGroup(tenantId, productSkuInfo.getSaleAttrs());
	    productSkuInfo.setUsableNum(usableNum==null?0:usableNum);
	       
        return productSkuInfo;
    }
    
    
    /**
     * 查询SKU单品信息(不包括可使用量,不变化的数据)
     * @param tenantId
     * @param skuId
     * @return
     */
    @Cacheable("orderProdImg")
	public ProductSkuInfo QuerySkuInfoByES(String tenantId, String skuId) {
		//获取商品信息
    	ProductSkuInfo productSkuInfo = SearchProdInfoUtils.querySkuInfo(tenantId, skuId);
		return productSkuInfo;
	}
}
