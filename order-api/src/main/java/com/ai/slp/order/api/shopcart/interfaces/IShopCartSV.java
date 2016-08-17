package com.ai.slp.order.api.shopcart.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.shopcart.param.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 购物车服务
 *
 * Date: 2016年5月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 *
 * @author liutong5
 */
@Path("/shopcart")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IShopCartSV {

    /**
     * 购物车中添加商品
     *
     * @param cartProd 购物车添加商品信息
     * @return 添加结果
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0100
     * @RestRelativeURL shopcart/addProd
     */
	@POST
	@Path("/addProd")
    public CartProdOptRes addProd(CartProd cartProd) throws BusinessException,SystemException;
    //@interface AddProd{}

    /**
     * 查询用户的购物车详细信息
     *
     * @param userInfo 用户信息
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0101
     * @RestRelativeURL shopcart/queryCartOfUser
     */
	@POST
	@Path("/queryCartOfUser")
    public CartProdList queryCartOfUser(UserInfo userInfo)throws BusinessException,SystemException;
    //@interface QueryCartOfUser{}

    /**
     * 调整购物车内商品数量
     *
     * @param cartProd 购物车更新商品信息
     * @return 操作结果
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0102
     * @RestRelativeURL shopcart/updateProdNum
     */
	@POST
	@Path("/updateProdNum")
    public CartProdOptRes updateProdNum(CartProd cartProd) throws BusinessException,SystemException;
    //@interface UpdateProdNum{}

    /**
     * 删除购物车商品,单个和批量都可以
     *
     * @param multiCartProd 购物车商品删除信息
     * @return 删除操作结果
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0103
     * @RestRelativeURL shopcart/deleteMultiProd
     */
	@POST
	@Path("/deleteMultiProd")
    public CartProdOptRes deleteMultiProd(MultiCartProd multiCartProd)throws BusinessException,SystemException;
    //@interface DeleteMultiProd{}

    /**
     * 查询用户的购物车概况信息
     *
     * @param userInfo 用户信息
     * @return
     * @throws BusinessException
     * @throws SystemException
     * @author liutng5
     * @ApiCode SHOP_CART_0104
     * @RestRelativeURL shopcart/queryPointsOfCart
     */
	@POST
	@Path("/queryPointsOfCart")
    public CartProdOptRes queryPointsOfCart(UserInfo userInfo)throws BusinessException,SystemException;
}
