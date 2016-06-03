package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;
import com.ai.slp.order.util.DateUtils;

@Service
@Transactional
public class OrdOrderBusiSVImpl implements IOrdOrderBusiSV {

    private static final Log LOG = LogFactory.getLog(OrdOrderBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrdOdFeeTotalAtomSV ordOdFeeTotalAtomSV;

    @Override
    public QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException {
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();

        criteria.andTenantIdEqualTo(orderListRequest.getTenantId());
        // 添加搜索条件
        if (orderListRequest.getOrderId() != 0) {
            criteria.andOrderIdEqualTo(orderListRequest.getOrderId());
        }
        if (!StringUtils.isBlank(orderListRequest.getOrderType())) {
            criteria.andOrderTypeEqualTo(orderListRequest.getOrderType());
        }
        if (!StringUtils.isBlank(orderListRequest.getState())) {
            criteria.andStateEqualTo(orderListRequest.getState());
        }
        if ((orderListRequest.getOrderTimeBegin() != null)
                && (orderListRequest.getOrderTimeEnd() != null)) {
            criteria.andOrderTimeBetween(
                    DateUtils.getTimestamp(orderListRequest.getOrderTimeBegin(),
                            "yyyy-MM-dd HH:mm:ss"),
                    DateUtils.getTimestamp(orderListRequest.getOrderTimeEnd(),
                            "yyyy-MM-dd HH:mm:ss"));
        }
        // 判断支付方式
        if (!StringUtils.isBlank(orderListRequest.getPayStyle())) {
            OrdOdFeeTotalCriteria example2 = new OrdOdFeeTotalCriteria();
            OrdOdFeeTotalCriteria.Criteria criteria2 = example2.createCriteria();
            criteria2.andTenantIdEqualTo(orderListRequest.getTenantId());
            criteria2.andPayStyleEqualTo(orderListRequest.getPayStyle());

            List<OrdOdFeeTotal> orderFeeTotalList = new ArrayList<OrdOdFeeTotal>();
            orderFeeTotalList = ordOdFeeTotalAtomSV.selectByExample(example2);
            List<Long> orderIdList = new ArrayList<Long>();
            for (OrdOdFeeTotal ordOdFeeTotal : orderFeeTotalList) {
                orderIdList.add(ordOdFeeTotal.getOrderId());
            }
            criteria.andOrderIdIn(orderIdList);
        }
        List<OrdOrder> list = new ArrayList<OrdOrder>();
        ResponseHeader responseHeader;
        Integer pageNo = orderListRequest.getPageNo();
        Integer pageSize = orderListRequest.getPageSize();
        int count = 0;
        try {
            count = ordOrderAtomSV.countByExample(example);
            list = ordOrderAtomSV.selectByExample(example);
            responseHeader = new ResponseHeader(true, "success", "查询成功");
        } catch (Exception e) {
            LOG.error("查询失败");
            responseHeader = new ResponseHeader(false, "fail", "查询失败");
        }
        List<OrdOrderVo> responseList = new ArrayList<OrdOrderVo>();
        for (OrdOrder order : list) {
            OrdOrderVo orderParams = new OrdOrderVo();
            BeanUtils.copyProperties(order, orderParams);
            responseList.add(orderParams);
        }
        PageInfo<OrdOrderVo> pageInfo = new PageInfo<OrdOrderVo>();
        QueryOrderListResponse response = new QueryOrderListResponse();
        pageInfo.setCount(count);
        pageInfo.setPageNo(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setResult(responseList);
        response.setPageInfo(pageInfo);
        response.setResponseHeader(responseHeader);
        return response;
    }

}
