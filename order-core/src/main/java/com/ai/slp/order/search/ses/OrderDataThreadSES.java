/*package com.ai.yc.order.search.ses;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.ses.SESClientFactory;
import com.ai.paas.ipaas.search.ISearchClient;
import com.ai.yc.order.constants.SearchConstants;
import com.ai.yc.order.search.bo.OrderInfo;

public class OrderDataThreadSES extends Thread{
	private static final Logger LOG=LoggerFactory.getLogger(OrderDataThreadSES.class);
    private List<OrderInfo> resultList;
    ISearchClient searchClient = SESClientFactory.getSearchClient(SearchConstants.SearchNameSpace);
    
    public OrderDataThreadSES(List<OrderInfo> resultList){
        this.resultList=resultList;
    }

	@Override
    public void run() {
    	searchClient.bulkInsert(resultList);
    	LOG.info("【"+Thread.currentThread().getName()+"】");
    } 
}
*/