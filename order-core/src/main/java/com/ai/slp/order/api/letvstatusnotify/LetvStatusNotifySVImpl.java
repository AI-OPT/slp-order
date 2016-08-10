package com.ai.slp.order.api.letvstatusnotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.letvstatusnotify.interfaces.ILetvStatusNotifySV;
import com.ai.slp.order.api.letvstatusnotify.param.LetvStatusNotifyRequest;
import com.ai.slp.order.service.business.interfaces.ILetvStatusNotifyBusiSV;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class LetvStatusNotifySVImpl implements ILetvStatusNotifySV{
	
	@Autowired
	private ILetvStatusNotifyBusiSV letvStatusNotifyBusiSV;
	
	@Override
	public BaseResponse statusnotify(LetvStatusNotifyRequest request) throws SystemException, BusinessException {
		BaseResponse response = letvStatusNotifyBusiSV.statusnotify(request);
		return response;
	}

}
