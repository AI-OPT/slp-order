package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.letvstatusnotify.param.LetvStatusNotifyRequest;

/**
 * 乐视订单状态通知逻辑
 * @author caofz
 *
 */
public interface ILetvStatusNotifyBusiSV {
	 public BaseResponse statusnotify(LetvStatusNotifyRequest request) throws BusinessException,
     SystemException;
}
