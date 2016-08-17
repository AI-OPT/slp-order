package com.ai.slp.order.service.business.interfaces;

import java.sql.Timestamp;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
/**
 * 订单退货
 * Date: 2016年6月27日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IOrderReturnGoodBusiSV {

    public void orderReturnGoods(OrdOrder ordOrder, Timestamp sysDate) throws BusinessException, SystemException;
}
