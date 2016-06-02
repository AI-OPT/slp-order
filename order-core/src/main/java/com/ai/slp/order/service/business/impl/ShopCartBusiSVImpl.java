package com.ai.slp.order.service.business.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdInfo;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.DateUtils;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.order.vo.ShopCartCachePointsVo;
import com.ai.slp.product.api.product.interfaces.IProductServerSV;
import com.ai.slp.product.api.product.param.ProductSkuInfo;
import com.ai.slp.product.api.product.param.SkuInfoQuery;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jackieliu on 16/5/17.
 */
@Service
@Transactional
public class ShopCartBusiSVImpl implements IShopCartBusiSV {
    private static Logger logger = LoggerFactory.getLogger(ShopCartBusiSVImpl.class);
    @Autowired
    IOrdOdCartProdAtomSV cartProdAtomSV;
//    @Reference(version="1.0.0")
//    IProductServerSV productServerSV;

    @PostConstruct
    public void shopCartProdMdsProcess(){
        IMsgProcessorHandler msgProcessorHandler=new IMsgProcessorHandler() {
            @Override
            public IMessageProcessor[] createInstances(int paramInt) {
                List<IMessageProcessor> processors = new ArrayList<>();
                IMessageProcessor processor = null;
                for (int i = 0; i < paramInt; i++) {
                    processor = new ShopCartMessProcessorImpl(cartProdAtomSV);
                    processors.add(processor);
                }
                return processors.toArray(new IMessageProcessor[processors.size()]);
            }
        };
        IMessageConsumer msgConsumer= MDSClientFactory.getConsumerClient(
                ShopCartConstants.MdsParams.SHOP_CART_TOPIC, msgProcessorHandler);
        msgConsumer.start();
    }

    /**
     * 查询用户购物车概览
     *
     * @param tenantId
     * @param userId
     * @return
     */
    @Override
    public CartProdOptRes queryCartOptions(String tenantId, String userId) {
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
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
    public CartProdOptRes addCartProd(CartProd cartProd) {
        //若购买数量为空,或小于0,则设置默认为1
        if (cartProd.getBuyNum() == null
                || cartProd.getBuyNum()<=0)
            cartProd.setBuyNum(1l);
        String tenantId = cartProd.getTenantId(),userId = cartProd.getUserId();
        checkSkuInfoTotal(tenantId,cartProd.getSkuId(),cartProd.getBuyNum());
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
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

        //添加/更新商品信息
        iCacheClient.hset(cartUserId,odCartProd.getSkuId(),JSON.toJSONString(odCartProd));
        //更新购物车上商品总数量
        pointsVo.setProdTotal(pointsVo.getProdTotal()+cartProd.getBuyNum());
        //更新概览
        iCacheClient.hset(cartUserId, ShopCartConstants.McsParams.CART_POINTS,JSON.toJSONString(pointsVo));
        sendCartProdMds(odCartProd);

        CartProdOptRes cartProdOptRes = new CartProdOptRes();
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
    public CartProdOptRes updateCartProd(CartProd cartProd) {
        //若购买数量为空,或小于0,则设置默认为1
        if (cartProd.getBuyNum() == null
                || cartProd.getBuyNum()<=0)
            cartProd.setBuyNum(1l);
        String tenantId = cartProd.getTenantId(),userId = cartProd.getUserId();
        checkSkuInfoTotal(tenantId,cartProd.getSkuId(),cartProd.getBuyNum());
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //若不存在,则直接进行添加操作
        if (!iCacheClient.hexists(cartUserId,cartProd.getSkuId())){
            return addCartProd(cartProd);
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
        sendCartProdMds(odCartProd);

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
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //若不存在购物车信息缓存,则建立缓存
        if (!iCacheClient.exists(cartUserId)){
            //从数据库中查询,建立缓存
            addShopCartCache(tenantId,userId);
        }
        List<String> failSkuList = new ArrayList<>();
        int delTotal = skuIdList.size(),delSuccessNum = 0;
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

            prod.setBuySum(0l);//商品数为零,表示删除
            sendCartProdMds(prod);
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
     *
     * @param tenantId
     * @param userId
     * @return
     */
    @Override
    public List<CartProdInfo> queryCartProdOfUser(String tenantId, String userId) {
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //若不存在购物车信息缓存,则建立缓存
        if (!iCacheClient.exists(cartUserId)){
            //从数据库中查询,建立缓存
            addShopCartCache(tenantId,userId);
        }
        //查询出缓存中购物车所有商品信息
        Map<String,String> cartProdMap = iCacheClient.hgetAll(cartUserId);
        //删除概览信息
        cartProdMap.remove(ShopCartConstants.McsParams.CART_POINTS);
        List<CartProdInfo> cartProdInfoList = new ArrayList<>();
        Iterator<String> skuIdIterator = cartProdMap.keySet().iterator();
        while (skuIdIterator.hasNext()){
            String skuId = skuIdIterator.next();
            String cartProdStr = cartProdMap.get(skuId);
            OrdOdCartProd cartProd = JSON.parseObject(cartProdStr,OrdOdCartProd.class);
            ProductSkuInfo skuInfo = querySkuInfo(tenantId,skuId);
            CartProdInfo prodInfo = new CartProdInfo();
            BeanUtils.copyProperties(prodInfo,skuInfo);
            prodInfo.setProductId(skuInfo.getProdId());
            prodInfo.setProductName(skuInfo.getProdName());
            prodInfo.setInsertTime(cartProd.getInsertTime());
            prodInfo.setBuyNum(cartProd.getBuySum().longValue());
            cartProdInfoList.add(prodInfo);
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
            addShopCartCache(tenantId,userId);
        }
        //查询概览信息
        String cartPrefix = iCacheClient.hget(IPassMcsUtils.genShopCartUserId(tenantId,userId)
                , ShopCartConstants.McsParams.CART_POINTS);
        ShopCartCachePointsVo pointsVo = JSON.parseObject(cartPrefix, ShopCartCachePointsVo.class);
        return pointsVo;
    }

    /**
     * 将数据库中数据加载到缓存中
     *
     * @param tenantId
     * @param userId
     */
    private void addShopCartCache(String tenantId,String userId){
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
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
     * 发送商城商品明细,用于更新数据库
     * @param prod
     */
    private void sendCartProdMds(OrdOdCartProd prod){
        IMessageSender msgSender = MDSClientFactory.getSenderClient(ShopCartConstants.MdsParams.SHOP_CART_TOPIC);

        msgSender.send(JSON.toJSONString(prod), 0);//第二个参数为分区键，如果不分区，传入0
    }

    /**
     * 检查SKU单品库存
     * @param tenantId
     * @param skuId
     * @return
     */
    private void checkSkuInfoTotal(String tenantId,String skuId,long buyNum){
//        ProductSkuInfo skuInfo = querySkuInfo(tenantId,skuId);
        //测试模拟返回结果
        ProductSkuInfo skuInfo = new ProductSkuInfo();
        skuInfo.setUsableNum(5);

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
        IProductServerSV productServerSV = DubboConsumerFactory.getService("iProductServerSv");
        return productServerSV.queryProductSkuById(skuInfoQuery);
    }
}
