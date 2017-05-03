package com.ai.slp.order.service.atom.interfaces;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;

public interface IOrdOrderAtomSV {
	//条件查询个数
    int countByExample(OrdOrderCriteria example);
    //查询集合数据
    List<OrdOrder> selectByExample(OrdOrderCriteria example);
    //查询未支付的订单
    List<OrdOrder> selectNotPayOrders(String tenantId,long orderId);
    //根据时间查询未支付的订单
    List<OrdOrder> selectNotPayOrdersByTime(Timestamp time);
    //查询父订单和订单业务类型为正常的订单
    List<OrdOrder> selectOtherOrders(OrdOrder ordOrder);
    //根据租户id和订单id查询订单
    public OrdOrder selectByOrderId(String tenantId,long orderId);
    //按主键查询订单
    OrdOrder selectByPrimaryKey(long orderId);
    //插入
    int insertSelective(OrdOrder record);
    //根据id修改
    int updateById(OrdOrder ordOrder);
    //查询子订单
    List<OrdOrder> selectChildOrder(String tenantId,long parentId);
    //查询售后订单
    List<OrdOrder> selectSaleOrder(String tenantId,long orderId);
    //根据状态查询订单
    List<OrdOrder> selectNotAuditFailureOrd(String tenantId,long orderId,String state);
    //修改订单
    public void updateStateByOrderId(String tenantId,Long orderId,String state);
    //查询合并订单
    List<OrdOrder> selectByBatchNo(long orderId,String tenantId,long batchNo);
    //根据状态和合并标识查询合并订单
    List<OrdOrder> selectMergeOrderByBatchNo(long orderId,String tenantId, long batchNo,String state) ;
    //修改
    int updateByExampleSelective(@Param("record") OrdOrder record, @Param("example") OrdOrderCriteria example);
    //通过原始订单查询订单
    public List<OrdOrder> selectOrderByOrigOrderId(long externalOrderId, long orderId);
    //修改
    int updateByPrimaryKeySelective(OrdOrder record);
    //修改
    int updateOrder(OrdOrder order);
    //修改订单状态
    int updateOrderState(OrdOrder record);
    //修改ofc订单
    int updateOFCOrder(OrdOrder record);
    //修改订单状态和合并表示
    int updateOrderStateAndBatchNo(OrdOrder record);
    //查询子订单
    List<OrdOrder> selectSubSaleOrder(long origOrderId,long orderId);
    //从数据库查询数据导入es中
    List<OrdOrder> selectSesData(int startSize,int size);
    //从数据库查询数据导入es的个数
    int countForSes();
    //查询订单部分信息
	OrdOrder selectPartInfo(Long orderId);
	//修改订单信息
	int updateInfoByRefund(OrdOrder ordOrder);
}
