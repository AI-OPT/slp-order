package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountIdParam;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.deposit.interfaces.IDepositSV;
import com.ai.slp.balance.api.deposit.param.DepositParam;
import com.ai.slp.balance.api.deposit.param.TransSummary;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderReturnGoodBusiSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumBackReq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Service
@Transactional
public class OrderReturnGoodsBusiSVImpl implements IOrderReturnGoodBusiSV {

    private static final Log LOG = LogFactory.getLog(OrderReturnGoodsBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;

    @Autowired
    private IOrdOdProdAtomSV ordOdProdAtomSV;

    @Autowired
    IOrdBalacneIfAtomSV ordBalacneIfAtomSV;

    @Autowired
    private IOrderFrameCoreSV orderFrameCoreSV;

    @Override
    public void orderReturnGoods(OrdOrder ordOrder, Timestamp sysDate) throws BusinessException,
            SystemException {
        long orderId = ordOrder.getOrderId();
        LOG.debug("开始处理-订单号[" + orderId + "]订单退货..");
        /* 1.创建退货订单表 */
        long returnOrderId = this.createReturnOrder(ordOrder, sysDate);
        /* 2.调用退款服务 */
        this.returnPay(ordOrder, sysDate, returnOrderId);
        /* 3.退回库存量 */
        this.backStorageNum(ordOrder);
        /* 4写入订单轨迹表 */
        this.writeOrderCreateStateChg(ordOrder.getTenantId(), sysDate, ordOrder.getOrderId(),
                ordOrder.getState());
    }

    /**
     * 创建退款订单
     * 
     * @param ordOrder
     * @param sysDate
     * @author zhangxw
     * @ApiDocMethod
     */
    private long createReturnOrder(OrdOrder ordOrder, Timestamp sysDate) {
        /* 1.创建退货订单表 */
        long subOrderId = SequenceUtil.createOrderId();
        OrdOrder returnGoodsOrdOrder = new OrdOrder();
        BeanUtils.copyProperties(returnGoodsOrdOrder, ordOrder);

        returnGoodsOrdOrder.setOrderId(subOrderId);
        returnGoodsOrdOrder.setBusiCode(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER);
        returnGoodsOrdOrder.setSubFlag(OrdersConstants.OrdOrder.SubFlag.NO);
        returnGoodsOrdOrder.setOrigOrderId(ordOrder.getOrderId());
        returnGoodsOrdOrder.setOrderTime(sysDate);
        returnGoodsOrdOrder.setState(OrdersConstants.OrdOrder.State.WAIT_REPAY);
        returnGoodsOrdOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.insertSelective(returnGoodsOrdOrder);
        return subOrderId;
    }

    /**
     * 调用退款服务
     * 
     * @param ordOrder
     * @param sysDate
     * @author zhangxw
     * @param returnOrderId
     * @ApiDocMethod
     */
    private void returnPay(OrdOrder ordOrder, Timestamp sysDate, long returnOrderId) {
        /* 1.调用退款 */
        OrdOdFeeTotal ordOdFeeTotal = this.getOrdOdFeeTotal(ordOrder.getTenantId(),
                ordOrder.getOrderId());
        DepositParam depositParam = new DepositParam();
        depositParam.setTenantId(ordOrder.getTenantId());
        depositParam.setSystemId("slp-order");
        long acctId = this.getAccountId(ordOrder.getTenantId(), ordOrder.getAcctId());
        depositParam.setAccountId(acctId);
        depositParam.setBusiSerialNo(String.valueOf(ordOrder.getOrderId()));
        depositParam.setSubsId(ordOrder.getSubsId());
        depositParam.setBusiDesc(ordOrder.getOrderType() + ordOdFeeTotal.getPaidFee());
        List<TransSummary> transSummaryList = new ArrayList<TransSummary>();
        TransSummary transSummary = new TransSummary();
        transSummary.setSubjectId(100000);
        transSummary.setAmount(ordOdFeeTotal.getPaidFee());
        transSummaryList.add(transSummary);
        depositParam.setTransSummary(transSummaryList);
        LOG.debug("订单存款：请求参数:" + JSON.toJSONString(depositParam));
        IDepositSV depositSV = DubboConsumerFactory.getService(IDepositSV.class);
        String depositFund = depositSV.depositFund(depositParam);
        LOG.debug("订单支付：存款流水:" + depositFund);
        if (StringUtil.isBlank(depositFund)) {
            throw new BusinessException("", "存款流水号为空[订单ID:" + ordOrder.getOrderId() + "]");
        }
        /* 2.更新订单状态为已退款 */
        ordOrder.setState(OrdersConstants.OrdOrder.State.FINISH_REPAY);
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        /* 3.记录费用信息 */
        this.saveFeeInfo(ordOdFeeTotal, ordOrder, depositFund, returnOrderId, sysDate);

    }

    /**
     * 记录存款流水号和费用汇总相关信息
     * 
     * @param ordOdFeeTotal
     * @param ordOrder
     * @param depositFund
     * @param returnOrderId
     * @author zhangxw
     * @param sysDate
     * @ApiDocMethod
     */
    private void saveFeeInfo(OrdOdFeeTotal ordOdFeeTotal, OrdOrder ordOrder, String depositFund,
            long returnOrderId, Timestamp sysDate) {
        /* 1.写入支付机构表 */
        long balacneIfId = SequenceUtil.createBalacneIfId();
        OrdBalacneIf ordBalacneIf = new OrdBalacneIf();
        ordBalacneIf.setBalacneIfId(balacneIfId);
        ordBalacneIf.setTenantId(ordOrder.getTenantId());
        ordBalacneIf.setOrderId(returnOrderId);
        ordBalacneIf.setPayStyle(ordOdFeeTotal.getPayStyle());
        ordBalacneIf.setPayFee(ordOdFeeTotal.getPaidFee());
        ordBalacneIf.setPaySystemId(OrdersConstants.OrdBalacneIf.paySystemId.PAY_CENTER);
        ordBalacneIf.setExternalId(depositFund);
        ordBalacneIf.setCreateTime(sysDate);
        ordBalacneIf.setRemark("支付成功");
        ordBalacneIfAtomSV.insertSelective(ordBalacneIf);
        /* 2.写入费用总表 */
        OrdOdFeeTotal ordOdFeeTotalNew = new OrdOdFeeTotal();
        BeanUtils.copyProperties(ordOdFeeTotalNew, ordOdFeeTotal);
        ordOdFeeTotalNew.setOrderId(returnOrderId);
        ordOdFeeTotalNew.setTenantId(ordOrder.getTenantId());
        ordOdFeeTotalNew.setPayFlag(OrdersConstants.OrdOdFeeTotal.payFlag.OUT);
        ordOdFeeTotalNew.setUpdateTime(sysDate);
        ordOdFeeTotalAtomSV.updateByOrderId(ordOdFeeTotalNew);
    }

    /**
     * 获取账户Id
     * 
     * @param tenantId
     * @param subsId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private long getAccountId(String tenantId, long acctId) {
        AccountIdParam accountIdParam = new AccountIdParam();
        accountIdParam.setTenantId(tenantId);
        accountIdParam.setAccountId(acctId);
        IAccountQuerySV accountQuerySV = DubboConsumerFactory.getService(IAccountQuerySV.class);
        AccountInfoVo accountInfoVo = accountQuerySV.queryAccontById(accountIdParam);
        if (accountInfoVo == null) {
            throw new BusinessException("", "账户信息为空[用户ID:" + acctId + "]");
        }
        return accountInfoVo.getAcctId();
    }

    /**
     * 获取费用总信息
     * 
     * @param tenantId
     * @param orderId
     * @return
     * @author zhangxw
     * @ApiDocMethod
     */
    private OrdOdFeeTotal getOrdOdFeeTotal(String tenantId, Long orderId) throws BusinessException,
            SystemException {
        OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(tenantId, orderId);
        if (ordOdFeeTotal == null) {
            throw new BusinessException("", "费用总表信息不存在[订单ID:" + orderId + "]");
        }
        return ordOdFeeTotal;
    }

    /**
     * 退回库存量
     * 
     * @param ordOrder
     * @param sysDate
     * @author zhangxw
     * @ApiDocMethod
     */
    private void backStorageNum(OrdOrder ordOrder) {
        List<OrdOdProd> ordOdProds = this.getOrdOdProds(ordOrder.getOrderId());
        if (CollectionUtil.isEmpty(ordOdProds))
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息["
                    + ordOrder.getOrderId() + "]");
        for (OrdOdProd ordOdProd : ordOdProds) {
            Map<String, Integer> storageNum = JSON.parseObject(ordOdProd.getSkuStorageId(),
                    new TypeReference<Map<String, Integer>>() {
                    });
            this.backStorageNum(ordOdProd.getTenantId(), ordOdProd.getSkuId(), storageNum);
        }
    }

    /**
     * 获取订单商品费用明细
     * 
     * @param orderId
     * @return
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    private List<OrdOdProd> getOrdOdProds(Long orderId) throws BusinessException, SystemException {
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        // 添加搜索条件
        if (orderId.intValue() != 0 && orderId != null) {
            criteria.andOrderIdEqualTo(orderId);
        }
        return ordOdProdAtomSV.selectByExample(example);
    }

    /**
     * 库存回退
     * 
     * @param tenantId
     * @param skuId
     * @param storageNum
     * @author zhangxw
     * @ApiDocMethod
     */
    private void backStorageNum(String tenantId, String skuId, Map<String, Integer> storageNum) {
        StorageNumBackReq storageNumBackReq = new StorageNumBackReq();
        storageNumBackReq.setTenantId(tenantId);
        storageNumBackReq.setSkuId(skuId);
        storageNumBackReq.setStorageNum(storageNum);
        IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
        BaseResponse response = iStorageNumSV.backStorageNum(storageNumBackReq);
        boolean success = response.getResponseHeader().isSuccess();
        String resultMessage = response.getResponseHeader().getResultMessage();
        if (!success)
            throw new BusinessException("", "调用回退库存异常:" + skuId + "错误信息如下:" + resultMessage + "]");

    }

    /**
     * 写入订单状态变化轨迹
     * 
     * @param request
     * @param sysDate
     * @param orderId
     * @author zhangxw
     * @param orgState
     * @ApiDocMethod
     */
    private void writeOrderCreateStateChg(String tenantId, Timestamp sysDate, long orderId,
            String orgState) {
        orderFrameCoreSV.ordOdStateChg(orderId, tenantId, orgState,
                OrdersConstants.OrdOrder.State.FINISH_REFUND,
                OrdOdStateChg.ChgDesc.FINISH_RETURN_GOODS, null, null, null, sysDate);

    }

}
