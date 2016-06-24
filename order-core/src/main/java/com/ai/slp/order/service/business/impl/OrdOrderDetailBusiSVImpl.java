package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.orderDetailList.param.OrdOrderDetailParams;
import com.ai.slp.order.api.orderDetailList.param.OrdOrderDetailParamsResponse;
import com.ai.slp.order.api.orderDetailList.param.OrderDetailListRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria.Criteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderDetailAtomSV;
import com.ai.slp.order.service.business.interfaces.IordOrderDetailBusiSV;

public class OrdOrderDetailBusiSVImpl implements IordOrderDetailBusiSV {

    @Autowired
    private IOrdOrderDetailAtomSV orderDetailAtomSV;

    /**
     * 快充话费订单详情
     */
    @Override
    public Map<String, Object> getFastChargeBillDetailInfo(OrderDetailListRequest orderDetailInfo) {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        OrdOrderCriteria example = new OrdOrderCriteria();
        Criteria cr = example.createCriteria();

        if (!StringUtil.isBlank(String.valueOf(orderDetailInfo.getParentOrderId()))) {
            cr.andParentOrderIdEqualTo(orderDetailInfo.getParentOrderId());
        }

        if (!StringUtil.isBlank(orderDetailInfo.getOrderType())) {
            cr.andOrderTypeEqualTo(orderDetailInfo.getOrderType());
        }
        if (!StringUtil.isBlank(orderDetailInfo.getState())) {
            cr.andStateEqualTo(orderDetailInfo.getState());
        }
        /**
         * 订单信息
         */
        List<OrdOrder> orderList = orderDetailAtomSV.selectByExample(example);

        /**
         * 通过父订单号得到子订单的Id
         */
        List<Long> idList = new ArrayList<Long>();
        for (OrdOrder ordOrder : orderList) {
            idList.add(ordOrder.getOrderId());
        }

        if (idList.size() == 0) {
            return null;
        }

        /**
         * 得到商品明细详情
         */
        OrdOdProdCriteria ordOdExample = new OrdOdProdCriteria();
        ordOdExample.or().andOrderIdIn(idList);
        List<OrdOdProd> ordOdProdList = orderDetailAtomSV.getOrdOdProdInfo(ordOdExample);

        /**
         * 充值信息
         */
        long phoneNum = 0;
        List<OrdOrderDetailParams> rechargeInformationList = new ArrayList<OrdOrderDetailParams>();
        for (int i = 0; i < orderList.size(); i++) {
            OrdOrder order = orderList.get(i);
            OrdOdProd ordOdProd = ordOdProdList.get(i);
            OrdOrderDetailParams orderDetail = new OrdOrderDetailParams();
            BeanUtils.copyProperties(order, orderDetail);
            orderDetail.setBuySum(ordOdProd.getBuySum());
            orderDetail.setSalePrice(ordOdProd.getSalePrice());
            orderDetail.setSubTotal(ordOdProd.getBuySum() * ordOdProd.getSalePrice());
            phoneNum = phoneNum + ordOdProd.getBuySum();
            rechargeInformationList.add(orderDetail);
        }

        /**
         * 获取订单费用信息
         */
        OrdOdFeeTotalCriteria ordOdFeeTotalExample = new OrdOdFeeTotalCriteria();
        ordOdFeeTotalExample.or().andOrderIdEqualTo(orderDetailInfo.getParentOrderId());
        List<OrdOdFeeTotal> ordOdFeeTotalList = orderDetailAtomSV
                .getOrdOdFeeTotalInfo(ordOdFeeTotalExample);
        OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalList.get(0);
        resultMap.put("totalFee", ordOdFeeTotal.getTotalFee());
        resultMap.put("payStyle", ordOdFeeTotal.getPayStyle());
        resultMap.put("phoneCount", phoneNum);
        resultMap.put("orderInfo", orderList);
        resultMap.put("rechargeInformation", rechargeInformationList);
        return resultMap;
    }

    /**
     * 明细
     */
    public OrdOrderDetailParamsResponse getDetailsInfo(OrdOrderDetailParams ordOrderParam) {
        OrdOdProdExtendCriteria example = new OrdOdProdExtendCriteria();
        example.or().andOrderIdEqualTo(ordOrderParam.getOrderId());
        List<OrdOdProdExtend> list = orderDetailAtomSV.getOrdOdProdExtend(example);
        List<OrdOrderDetailParams> result = new ArrayList<OrdOrderDetailParams>();
        /**
         * 组装数据
         */
        for (int i = 0; i < list.size(); i++) {
            OrdOrderDetailParams detailParams = new OrdOrderDetailParams();
            OrdOdProdExtend ordOdProdExtend = list.get(i);
            detailParams.setBasicOrgId(ordOrderParam.getBasicOrgId());
            detailParams.setCityCode(ordOrderParam.getCityCode());
            detailParams.setPhoneNum(ordOdProdExtend.getInfoJson());
            detailParams.setSalePrice(ordOrderParam.getSalePrice());
            result.add(detailParams);
        }
        PageInfo<OrdOrderDetailParams> pageInfo = new PageInfo<OrdOrderDetailParams>();
        pageInfo.setResult(result);
        OrdOrderDetailParamsResponse response = new OrdOrderDetailParamsResponse();
        response.setDetailsList(pageInfo);
        return response;
    }

    /**
     * 快充流量订单详情
     */
    @Override
    public PageInfo<OrdOrderDetailParams> getFastChargeFlowDetailInfo(
            OrderDetailListRequest orderDetailInfo) {

        return null;
    }

    /**
     * 快充充值卡订单详情
     */
    @Override
    public PageInfo<OrdOrderDetailParams> getRechargeCardsDetailInfo(
            OrderDetailListRequest orderDetailInfo) {

        return null;
    }

}
