package com.ai.slp.order.service.atom.interfaces;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;

public interface IOrdOrderAtomSV {

    int countByExample(OrdOrderCriteria example);

    List<OrdOrder> selectByExample(OrdOrderCriteria example);
  
    List<OrdOrder> selectNotPayOrders(String tenantId,long orderId);
    
    List<OrdOrder> selectNotPayOrdersByTime(Timestamp time);
    
    List<OrdOrder> selectOtherOrders(OrdOrder ordOrder);
    
    public OrdOrder selectByOrderId(String tenantId,long orderId);
    
    OrdOrder selectByPrimaryKey(long orderId);

    int insertSelective(OrdOrder record);

    int updateById(OrdOrder ordOrder);
    
    List<OrdOrder> selectChildOrder(String tenantId,long parentId);
    
    List<OrdOrder> selectSaleOrder(String tenantId,long orderId);

    List<OrdOrder> selectNotAuditFailureOrd(String tenantId,long orderId,String state);
    
    public void updateStateByOrderId(String tenantId,Long orderId,String state);
    
    List<OrdOrder> selectByBatchNo(long orderId,String tenantId,long batchNo);
    
    List<OrdOrder> selectMergeOrderByBatchNo(long orderId,String tenantId, long batchNo,String state) ;
    
    int updateByExampleSelective(@Param("record") OrdOrder record, @Param("example") OrdOrderCriteria example);
    
    public List<OrdOrder> selectOrderByOrigOrderId(long externalOrderId, long orderId);
    
    int updateByPrimaryKeySelective(OrdOrder record);
    
    int updateOrder(OrdOrder order);
    
    int updateOrderState(OrdOrder record);
    
    int updateOFCOrder(OrdOrder record);
    
    int updateOrderStateAndBatchNo(OrdOrder record);
    
    List<OrdOrder> selectSubSaleOrder(long origOrderId,long orderId);
    
    List<OrdOrder> selectSesData(int startSize,int size);
    
    int countForSes();

	OrdOrder selectPartInfo(Long orderId);

	int updateInfoByRefund(OrdOrder ordOrder);
}
