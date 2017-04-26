package com.ai.slp.order.service.business.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdInfo;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.constants.ErrorCodeConstants;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.manager.CacheClientManager;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.DateUtils;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.order.vo.ShopCartCachePointsVo;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
import com.alibaba.fastjson.JSON;

/**
 * Created by jackieliu on 16/5/17.
 */
@Service
@Transactional
public class ShopCartBusiSVImpl implements IShopCartBusiSV {
	private static Logger logger = LoggerFactory.getLogger(ShopCartBusiSVImpl.class);
    @Autowired
    IOrdOdCartProdAtomSV cartProdAtomSV;

    /**
     * 查询用户购物车概览
     *
     * @param tenantId
     * @param userId
     * @return
     */
    @Override
    public CartProdOptRes queryCartOptions(String tenantId, String userId) {
        ICacheClient iCacheClient = CacheClientManager.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        CartProdOptRes cartProdOptRes = new CartProdOptRes();
        ShopCartCachePointsVo pointsVo = queryCartPoints(iCacheClient,tenantId,userId);
        BeanUtils.copyProperties(cartProdOptRes,pointsVo);
        return cartProdOptRes;
    }

    /**
     * 购物车添加商品
     *
     * @param cartProd
     * @return
     */
    @Override
    public CartProdOptRes addCartProd(OrdOdCartProd odCartProd, ShopCartCachePointsVo pointsVo) {
    	CartProdOptRes cartProdOptRes = null;
     /*   //若商品数量为空或零,删除购物车中商品
        if (odCartProd.getBuySum()==null || new Long(0l).equals(odCartProd.getBuySum())){
            cartProdAtomSV.deleteByProdId(odCartProd.getTenantId(),odCartProd.getUserId(),odCartProd.getSkuId());
        }else {
            OrdOdCartProd cartProd0 = cartProdAtomSV.queryByProdOfCart(odCartProd.getTenantId(),odCartProd.getUserId(),odCartProd.getSkuId());
            //若没有添加商品,则直接添加
            if (cartProd0 ==null){
                cartProdAtomSV.installCartProd(odCartProd);
            }else {
                cartProd0.setBuySum(odCartProd.getBuySum());
                cartProdAtomSV.updateCartProdById(cartProd0);
            }
        }*/
        cartProdOptRes = new CartProdOptRes();
        BeanUtils.copyProperties(cartProdOptRes,pointsVo);
        return cartProdOptRes;
    }

    /**
     * 更新购物车中商品数量
     *
     * @param cartProd
     * @return
     */
    @Override
    public CartProdOptRes updateCartProd(CartProd cartProd, ICacheClient iCacheClient, String cartUserId) {
        String tenantId = cartProd.getTenantId(),userId = cartProd.getUserId();
        //若不存在,则直接进行添加操作
        if (!iCacheClient.hexists(cartUserId,cartProd.getSkuId())){
            return addCartProd(cartProd,iCacheClient,cartUserId);
        }
        String cartProdStr = iCacheClient.hget(cartUserId,cartProd.getSkuId());
        //更新商品数量
        OrdOdCartProd odCartProd = JSON.parseObject(cartProdStr, OrdOdCartProd.class);
        //此商品变化的数量,若为负数,则表示减少
        long addNum = cartProd.getBuyNum() - odCartProd.getBuySum();
        //更新购买数量
        odCartProd.setBuySum(cartProd.getBuyNum());
        //添加/更新商品信息
        iCacheClient.hset(cartUserId,odCartProd.getSkuId(),JSON.toJSONString(odCartProd));
        //查询用户购物车概览
        ShopCartCachePointsVo pointsVo = queryCartPoints(iCacheClient,tenantId,userId);
        pointsVo.setProdTotal(pointsVo.getProdTotal()+addNum);//更新商品总数量
        //更新概览
        iCacheClient.hset(cartUserId, ShopCartConstants.McsParams.CART_POINTS,JSON.toJSONString(pointsVo));
     /*   //若商品数量为空或零,删除购物车中商品
        if (odCartProd.getBuySum()==null || new Long(0l).equals(odCartProd.getBuySum())){
            cartProdAtomSV.deleteByProdId(odCartProd.getTenantId(),odCartProd.getUserId(),odCartProd.getSkuId());
        }else {
            OrdOdCartProd cartProd0 = cartProdAtomSV.queryByProdOfCart(odCartProd.getTenantId(),odCartProd.getUserId(),odCartProd.getSkuId());
            //若没有添加商品,则直接添加
            if (cartProd0 ==null){
                cartProdAtomSV.installCartProd(odCartProd);
            }else {
                cartProd0.setBuySum(odCartProd.getBuySum());
             //   cartProdAtomSV.updateCartProdById(cartProd0);
                cartProdAtomSV.updateCartProdSum(cartProd0);
            }
        }*/
        CartProdOptRes cartProdOptRes = new CartProdOptRes();
        BeanUtils.copyProperties(cartProdOptRes,pointsVo);
        return cartProdOptRes;
    }

    /**
     * 删除购物车中商品
     *
     * @param tenantId
     * @param userId
     * @param skuIdList
     * @return
     */
    @Override
    public CartProdOptRes deleteCartProd(String tenantId, String userId, List<String> skuIdList) {
        ICacheClient iCacheClient = CacheClientManager.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
       /* //若不存在购物车信息缓存,则建立缓存
        if (!iCacheClient.exists(cartUserId)){
            //从数据库中查询,建立缓存
            addShopCartCache(tenantId,userId);
        }*/
        List<String> failSkuList = new ArrayList<>();
        int delTotal = skuIdList.size(),delSuccessNum = 0;
        
        Collections.sort(skuIdList, Collator.getInstance(java.util.Locale.CHINA));
        //循环删除商品
        for (String skuId:skuIdList){
            String cartProdStr = iCacheClient.hget(cartUserId,skuId);
            //若不包含此商品,则直接跳过
            if (StringUtils.isBlank(cartProdStr)){
                failSkuList.add(skuId);
                continue;
            }
            OrdOdCartProd prod = JSON.parseObject(cartProdStr,OrdOdCartProd.class);
            ShopCartCachePointsVo pointsVo = queryCartPoints(iCacheClient,tenantId,userId);
            pointsVo.setProdNum(pointsVo.getProdNum()-1);
            pointsVo.setProdTotal(pointsVo.getProdTotal()-prod.getBuySum());
            iCacheClient.hdel(cartUserId,skuId);
            iCacheClient.hset(cartUserId, ShopCartConstants.McsParams.CART_POINTS,JSON.toJSONString(pointsVo));
            delSuccessNum++;

         /*   prod.setBuySum(0l);//商品数为零,表示删除
            //若商品数量为空或零,删除购物车中商品
            if (prod.getBuySum()==null || new Long(0l).equals(prod.getBuySum())){
                cartProdAtomSV.deleteByProdId(prod.getTenantId(),prod.getUserId(),prod.getSkuId());
            }*/

        }
        CartProdOptRes optRes = new CartProdOptRes();
        ShopCartCachePointsVo cachePointsVo = queryCartPoints(iCacheClient,tenantId,userId);
        BeanUtils.copyProperties(optRes,cachePointsVo);
        optRes.setDelProdTotal(delTotal);
        optRes.setDelSuccessNum(delSuccessNum);
        optRes.setFailProdIdList(failSkuList);
        return optRes;
    }

    /**
     * 查询用户购物车中商品的信息
     * @param tenantId
     * @param userId
     * @return
     */
    @Override
    public List<CartProdInfo> queryCartProdOfUser(String tenantId, String userId, Map<String,String> cartProdMap ) {
        List<CartProdInfo> cartProdInfoList = new ArrayList<>();
        Iterator<String> skuIdIterator = cartProdMap.keySet().iterator();
        while (skuIdIterator.hasNext()){
            String skuId = skuIdIterator.next();
            String cartProdStr = cartProdMap.get(skuId);
            OrdOdCartProd cartProd = JSON.parseObject(cartProdStr,OrdOdCartProd.class);
            try {
            	cartProdInfoList = this.queryCartProdInfoList(cartProdInfoList, skuId, 
            			tenantId, cartProd);
            }catch (BusinessException e){
                //若SKU不存在或无效
                //若销售商品不存在
                if (ErrorCodeConstants.Product.SKU_NO_EXIST.equals(e.getErrorCode())
                        || ErrorCodeConstants.Product.SKU_NO_EXIST.equals(e.getErrorCode())){
                    List<String> skuIdList = new ArrayList<>();
                    skuIdList.add(skuId);
                    deleteCartProd(tenantId,userId,skuIdList);
                }else {
                    throw new BusinessException(e);
                }
            }
        }
        //查询SKU信息
        return cartProdInfoList;
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
     * 检查SKU单品库存
     * @param skuInfo
     * @param buyNum
     * @return
     */
    private void checkSkuInfoTotal(ProductSkuInfo skuInfo,long buyNum){
        if (skuInfo==null || skuInfo.getUsableNum()<=0){
            throw new BusinessException("","商品已售罄或下架");
        }
        if ( buyNum>skuInfo.getUsableNum()){
            logger.warn("单品库存{},检查库存{}",skuInfo.getUsableNum(),buyNum);
            throw new BusinessException("","商品库存不足["+buyNum+"]");
        }
    }

    /**
     * 查询SKU单品信息
     * @param tenantId
     * @param skuId
     * @return
     */
    private ProductSkuInfo querySkuInfo(String tenantId,String skuId){
        SkuInfoQuery skuInfoQuery = new SkuInfoQuery();
        skuInfoQuery.setTenantId(tenantId);
        skuInfoQuery.setSkuId(skuId);
        IProductServerSV productServerSV = DubboConsumerFactory.getService(IProductServerSV.class);
        return productServerSV.queryProductSkuById4ShopCart(skuInfoQuery);
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
            logger.error("获取配置信息失败",e);
            e.printStackTrace();
        }
        return Integer.parseInt(limitNum);
    }
    
    
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public List<CartProdInfo> queryCartProdInfoList( List<CartProdInfo> cartProdInfoList,
    		String skuId,String tenantId,OrdOdCartProd cartProd) {
    	ProductSkuInfo skuInfo = querySkuInfo(tenantId, skuId);
        if(skuInfo==null) {
        	throw new BusinessException("", "sku单品信息不存在");
        }
        if(skuInfo!=null&&skuInfo.getUsableNum()==null) {
        	skuInfo.setUsableNum(0l);
        }
        if(skuInfo!=null&&skuInfo.getSalePrice()==null) {
        	skuInfo.setSalePrice(0l);
        }
        CartProdInfo prodInfo = new CartProdInfo();
        BeanUtils.copyProperties(prodInfo,skuInfo);
        prodInfo.setProductId(skuInfo.getProdId());
        prodInfo.setProductName(skuInfo.getProdName());
        prodInfo.setInsertTime(cartProd.getInsertTime());
        prodInfo.setBuyNum(cartProd.getBuySum().longValue());
        prodInfo.setSupplierId(cartProd.getSupplierId());
        //若库存量大于0,且小于购物车添加数量,则使用库存量
        if (skuInfo.getUsableNum()>0 && skuInfo.getUsableNum()<prodInfo.getBuyNum()){
            prodInfo.setBuyNum(skuInfo.getUsableNum());
        }
        cartProdInfoList.add(prodInfo);
		return cartProdInfoList;
    }
    
    
    
    
    /**
     * 添加商品(更新操作时若不存在,则直接进行添加操作)
     * @param cartProd
     * @param iCacheClient
     * @param cartUserId
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    private CartProdOptRes addCartProd(CartProd cartProd,ICacheClient iCacheClient, String cartUserId) {
    	CartProdOptRes cartProdOptRes = null;
    	String tenantId = cartProd.getTenantId(),userId = cartProd.getUserId();
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
        //TODO 查询商品信息需要优化
        ProductSkuInfo skuInfo = querySkuInfo(tenantId,cartProd.getSkuId());
        checkSkuInfoTotal(skuInfo,odCartProd.getBuySum());
        if (StringUtils.isBlank(cartProdStr))
            odCartProd.setSupplierId(skuInfo.getSupplierId());
        //添加/更新商品信息
        iCacheClient.hset(cartUserId,odCartProd.getSkuId(),JSON.toJSONString(odCartProd));
        //更新购物车上商品总数量
        pointsVo.setProdTotal(pointsVo.getProdTotal()+cartProd.getBuyNum());
        
        //更新概览
        iCacheClient.hset(cartUserId, ShopCartConstants.McsParams.CART_POINTS,JSON.toJSONString(pointsVo));
        
        //若商品数量为空或零,删除购物车中商品
      /*  if (odCartProd.getBuySum()==null || new Long(0l).equals(odCartProd.getBuySum())){
            cartProdAtomSV.deleteByProdId(odCartProd.getTenantId(),odCartProd.getUserId(),odCartProd.getSkuId());
        }else {
            OrdOdCartProd cartProd0 = cartProdAtomSV.queryByProdOfCart(odCartProd.getTenantId(),odCartProd.getUserId(),odCartProd.getSkuId());
            //若没有添加商品,则直接添加
            if (cartProd0 ==null){
                cartProdAtomSV.installCartProd(odCartProd);
            }else {
                cartProd0.setBuySum(odCartProd.getBuySum());
                cartProdAtomSV.updateCartProdById(cartProd0);
            }
        }*/

        cartProdOptRes = new CartProdOptRes();
        BeanUtils.copyProperties(cartProdOptRes,pointsVo);
        return cartProdOptRes;
    }
}
