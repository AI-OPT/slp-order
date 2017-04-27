package com.ai.slp.order.dao.mapper.attach;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

/**
 * 查询订单信息 Date: 2016年6月29日
 * 
 * @author caofz
 * 
 */
public interface OrdOrderAttachMapper {
	  
    /**
     * 用户消费积分状态通知服务
     * @param OrdOrder
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_order set DOWNSTREAM_ORDER_ID = #{downstreamOrderId} where ORDER_ID = #{orderId} ")
	public int updateOrdOrder(OrdOrder order);
    
    /**
     * OFC售后订单状态通知
     * @param OrdOrder
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_order set REMARK=#{remark},STATE=#{state} where ORDER_ID = #{orderId} ")
    public int updateOFCOrder(OrdOrder OrdOrder);
    
    /**
     * 修改订单状态
     * @param record
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_order set STATE=#{state},STATE_CHG_TIME = #{stateChgTime} where ORDER_ID = #{orderId} ")
	public int updateOrderState(OrdOrder record);
    
    
    /**
     * 修改订单状态及批次号
     * @param record
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_order set STATE=#{state},STATE_CHG_TIME = #{stateChgTime}, BATCH_NO = #{batchNo}  where ORDER_ID = #{orderId} ")
	public int updateOrderStateAndBatchNo(OrdOrder record);
    
    /**
     * 修改订单商品售后标识
     * @param ordOdProd
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_od_prod set CUS_SERVICE_FLAG = #{cusServiceFlag} where ORDER_ID = #{orderId} ")
	public int updateCusServiceFlag(OrdOdProd ordOdProd);
    
    
    /**
     * 更新购物车数量
     * @param cartProd0
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_od_cart_prod set BUY_SUM = #{buySum} where PROD_DETAL_ID = #{prodDetalId}")
	public void updateCartProdSum(OrdOdCartProd cartProd0);
    
    
    @Select("select ORDER_ID,TENANT_ID,PARENT_ORDER_ID from ord_order where ORDER_ID = #{orderId}")
    @Results(value = {  
            @Result(id = true, property = "orderId", column = "ORDER_ID"),  
            @Result(property = "tenantId", column = "TENANT_ID"),  
    		@Result(property = "parentOrderId", column = "PARENT_ORDER_ID") })  
	public OrdOrder selectPartInfo(Long orderId);
    
    /**
     * 同意退款金额修改涉及到订单信息的更改
     * @param ordOrder
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    @Update("update ord_order set OPER_ID = #{operId},REASON_DESC=#{reasonDesc} where ORDER_ID = #{orderId}")
	public int updateInfoByRefund(OrdOrder ordOrder);
}
