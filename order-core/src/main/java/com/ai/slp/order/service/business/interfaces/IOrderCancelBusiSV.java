package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
/**
 * 超过30分钟未支付订单自动关闭实现
 * Date: 2016年6月23日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public interface IOrderCancelBusiSV {

    public void orderCancel(OrdOrder ordOrder) throws BusinessException, SystemException;
}
