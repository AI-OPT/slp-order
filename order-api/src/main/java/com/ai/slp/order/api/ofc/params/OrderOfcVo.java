package com.ai.slp.order.api.ofc.params;

import com.ai.slp.order.api.ofc.params.OrdOdFeeTotalVo;
import com.ai.slp.order.api.ofc.params.OrdOdLogisticsVo;
import com.ai.slp.order.api.ofc.params.OrdOrderOfcVo;

/**
 * 订单信息,包括订单费用信息/订单信息/订单出货信息 
 * Date: 2016年11月12日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
public class OrderOfcVo {

	/**
	 * 订单信息
	 */
	private OrdOrderOfcVo orOrderOfcVo;

	/**
	 * 订单费用信息
	 */
	private OrdOdFeeTotalVo ordOdFeeTotalVo;

	/**
	 * 订单出货信息
	 */
	private OrdOdLogisticsVo ordOdLogisticsVo;

	public OrdOrderOfcVo getOrOrderOfcVo() {
		return orOrderOfcVo;
	}

	public void setOrOrderOfcVo(OrdOrderOfcVo orOrderOfcVo) {
		this.orOrderOfcVo = orOrderOfcVo;
	}

	public OrdOdFeeTotalVo getOrdOdFeeTotalVo() {
		return ordOdFeeTotalVo;
	}

	public void setOrdOdFeeTotalVo(OrdOdFeeTotalVo ordOdFeeTotalVo) {
		this.ordOdFeeTotalVo = ordOdFeeTotalVo;
	}

	public OrdOdLogisticsVo getOrdOdLogisticsVo() {
		return ordOdLogisticsVo;
	}

	public void setOrdOdLogisticsVo(OrdOdLogisticsVo ordOdLogisticsVo) {
		this.ordOdLogisticsVo = ordOdLogisticsVo;
	}

}
