/*package com.ai.slp.order.search.ses;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.sdk.components.ses.base.AbstractSES;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderIndexBusiSV;
import com.alibaba.fastjson.JSON;

*//**
 * 刷新搜索引擎数据
 *//*
@Component
public class OrderDataSES extends AbstractSES {
	private static final Logger LOG=LoggerFactory.getLogger(OrderDataSES.class);

    private static final int MAX_SIZE = 1000;
    @Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	@Autowired
	private IOrderIndexBusiSV orderIndexBusiSV;
	@Override
    public void write() throws Exception {
        ExecutorService pool = null;
        try {
            LOG.info(">>>>>>SES刷新数据开始");
            pool = Executors.newCachedThreadPool();

            List<OrderInfo> orderInfoList;
            int start = 0;
            while (true) {
                // 获取指定条目的order信息
               List<OrdOrder> orderList = ordOrderAtomSV.findOrdList(start,MAX_SIZE);
                orderInfoList = orderIndexBusiSV.orderFillQuery(orderList);

                if (!orderInfoList.isEmpty()) {
                    Thread t = new OrderDataThreadSES(orderInfoList);
                    pool.execute(t);
                    LOG.info("SESData:" + JSON.toJSONString(orderInfoList));
                }
                if (orderInfoList.size() < MAX_SIZE) {
                    break;
                }

                start += MAX_SIZE;
            }
            LOG.info(">>>>>>SES刷新数据结束");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally{
            if(pool!=null){
                pool.shutdown();
            }

        }
    }

}
*/