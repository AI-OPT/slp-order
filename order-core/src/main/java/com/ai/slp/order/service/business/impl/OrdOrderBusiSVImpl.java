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
import com.ai.slp.order.api.orderlist.param.OrdOrderParams;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrdOrderBusiSV;
import com.ai.slp.order.util.DateUtils;

@Service
@Transactional
public class OrdOrderBusiSVImpl implements IOrdOrderBusiSV {

    private static final Log LOG = LogFactory.getLog(OrdOrderBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Override
    public QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest)
            throws BusinessException, SystemException {
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();

        criteria.andTenantIdEqualTo(orderListRequest.getTenantId());

        if (orderListRequest.getParentOrderId() != 0) {
            criteria.andParentOrderIdEqualTo(orderListRequest.getParentOrderId());
        }
        if (!StringUtils.isBlank(orderListRequest.getSubFlag())) {
            criteria.andSubFlagEqualTo(orderListRequest.getSubFlag());
        }
        if (!StringUtils.isBlank(orderListRequest.getOrderType())) {
            criteria.andOrderTypeEqualTo(orderListRequest.getOrderType());
        }
        if ((orderListRequest.getOrderTimeBegin() != null)
                && (orderListRequest.getOrderTimeEnd() != null)) {
            criteria.andOrderTimeBetween(
                    DateUtils.getTimestamp(orderListRequest.getOrderTimeBegin(),
                            "yyyy-MM-dd HH:mm:ss"),
                    DateUtils.getTimestamp(orderListRequest.getOrderTimeEnd(),
                            "yyyy-MM-dd HH:mm:ss"));
        }
        if ((orderListRequest.getOrderChgBegin() != null)
                && (orderListRequest.getOrderChgEnd() != null)) {
            criteria.andStateChgTimeBetween(
                    DateUtils.getTimestamp(orderListRequest.getOrderTimeBegin(),
                            "yyyy-MM-dd HH:mm:ss"),
                    DateUtils.getTimestamp(orderListRequest.getOrderTimeEnd(),
                            "yyyy-MM-dd HH:mm:ss"));
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
        List<OrdOrderParams> responseList = new ArrayList<OrdOrderParams>();
        for (OrdOrder order : list) {
            OrdOrderParams orderParams = new OrdOrderParams();
            BeanUtils.copyProperties(order, orderParams);
            responseList.add(orderParams);
        }
        PageInfo<OrdOrderParams> pageInfo = new PageInfo<OrdOrderParams>();
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
