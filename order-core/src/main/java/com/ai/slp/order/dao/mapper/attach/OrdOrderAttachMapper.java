package com.ai.slp.order.dao.mapper.attach;

import org.apache.ibatis.annotations.Update;

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
	public int updateOrdOrder(OrdOrder OrdOrder);
    
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
}
