package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;

public interface IOrdOrderAtomSV {

    int countByExample(OrdOrderCriteria example);

    List<OrdOrder> selectByExample(OrdOrderCriteria example);
    
    public OrdOrder selectByOrderId(String tenantId,long orderId);

    int insertSelective(OrdOrder record);

    int updateById(OrdOrder ordOrder);
    
    List<OrdOrder> selectChildOrder(String tenantId,long parentId);
    
    List<OrdOrder> selectSaleOrder(String tenantId,long orderId);
    
    public void updateStateByOrderId(String tenantId,Long orderId,String state);
    
    List<OrdOrder> selectByBatchNo(long orderId,String tenantId,long batchNo);
    
    List<OrdOrder> selectMergeOrderByBatchNo(long orderId,String tenantId, long batchNo,String state) ;
    
    int updateByExampleSelective(@Param("record") OrdOrder record, @Param("example") OrdOrderCriteria example);
    
    public List<OrdOrder> selectOrderByOrigOrderId(long externalOrderId, long orderId);
}
