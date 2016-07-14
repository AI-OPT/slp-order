package com.ai.slp.order.api.shopcart.impl;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.*;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.alibaba.dubbo.config.annotation.Service;
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
        try {
            optRes = shopCartBusiSV.addCartProd(cartProd);
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
            List<CartProdInfo> prodInfos = shopCartBusiSV.queryCartProdOfUser(userInfo.getTenantId(),userInfo.getUserId());
            prodList.setProdInfoList(prodInfos);
            prodList.setResponseHeader(new ResponseHeader(true,ExceptCodeConstants.Special.SUCCESS,"OK"));
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
            optRes = shopCartBusiSV.updateCartProd(cartProd);
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
        }catch (BusinessException|SystemException e){
            optRes = new CartProdOptRes();
            optRes.setResponseHeader(new ResponseHeader(false,e.getErrorCode(),e.getMessage()));
        }
        return optRes;
    }
}
