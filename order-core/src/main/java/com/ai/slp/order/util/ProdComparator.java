package com.ai.slp.order.util;

import java.util.Comparator;

import com.ai.slp.order.dao.mapper.bo.OrdOdProd;

public class ProdComparator implements Comparator<OrdOdProd>{

	@Override
	public int compare(OrdOdProd o1, OrdOdProd o2) {
		if(null==o1||null==o2) {
			return 0;
		}
		return (new Long(o1.getProdDetalId())).compareTo(o2.getProdDetalId());
	}
}
