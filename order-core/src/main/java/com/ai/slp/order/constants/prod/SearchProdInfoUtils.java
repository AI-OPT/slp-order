package com.ai.slp.order.constants.prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.search.ISearchClient;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.SearchOption;
import com.ai.slp.order.api.shopcart.param.ProductSkuInfo;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.prod.ImageInfo;
import com.ai.slp.order.search.bo.prod.SKUInfo;
import com.ai.slp.order.util.prod.IPaasStorageUtils;

public class SearchProdInfoUtils {
	 
	/**
	 * 查询商品信息
	 * @param tenantId
	 * @param skuId
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	 public static ProductSkuInfo querySkuInfo(String tenantId,String skuId){
	        ProductSkuInfo productSkuInfo=new ProductSkuInfo();
	        List<SKUInfo> prodInfos = searchProdInfo(tenantId, skuId);
	        if(!CollectionUtil.isEmpty(prodInfos)) {
	        	SKUInfo skuInfo = prodInfos.get(0);
	        	productSkuInfo.setSupplierId(skuInfo.getSupplierid());
	        	productSkuInfo.setSkuName(skuInfo.getSkuname());
	        	productSkuInfo.setProdId(skuInfo.getProductid());
	        	productSkuInfo.setSkuId(skuId);
	        	productSkuInfo.setProdName(skuInfo.getProductname());
	        	productSkuInfo.setState(skuInfo.getState());
	        	productSkuInfo.setSalePrice(skuInfo.getPrice());
	        	ImageInfo imageinfo = skuInfo.getImageinfo();
	        	productSkuInfo.setVfsId(imageinfo.getVfsid());
	        	productSkuInfo.setPicType(imageinfo.getImagetype());
	        	//设置库存组id
	        	productSkuInfo.setStorageGroupId(skuInfo.getStoragegroupid());
	        }
	        return productSkuInfo;
	    }
	 
	 

	    //es查询商品信息
	    private static List<SKUInfo> searchProdInfo(String tenantId,String skuId) {
	    	  List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
	      	searchCriterias.add(new SearchCriteria("tenantid",tenantId,
	      			new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
	      	searchCriterias.add(new SearchCriteria("productid",skuId,
	      			new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
	      	ISearchClient searchClient = ESClientManager.getSesClient(SearchConstants.ProdSearchNameSpace);
	      	Result<SKUInfo> result = searchClient.search(searchCriterias, 0, 1,null,SKUInfo.class);
	      	List<SKUInfo> skuList = result.getContents();
	      	return skuList;
	    }
	    
	    
	    /**
	     * 查询库存组可用量
	     * @param tenantId
	     * @param groupId
	     * @return
	     * @author caofz
	     * @ApiDocMethod
	     * @ApiCode 
	     * @RestRelativeURL
	     */
	    public static Long queryNowUsableNumOfGroup(String tenantId, String groupId) {
	        ICacheClient cacheClient = IPaasStorageUtils.getClient();
	        //获取库存组的cacheKey
	        String groupKey = IPaasStorageUtils.genMcsStorageGroupKey(tenantId,groupId);
	        //使用当前优先级
	        String priority = getPromotionPriority(cacheClient,tenantId,groupId);
	        //优先级价格对应KEY
	        String priceKey = IPaasStorageUtils.genMcsGroupSerialPriceKey(tenantId,groupId,priority);
	        //优先级中库存可用量对应KEY
	        String priorityUsable = IPaasStorageUtils.genMcsPriorityUsableKey(tenantId,groupId,priority);
	        /* 以下情况使用正常优先级
	         *  .未找到促销优先级
	         *  .促销价格不存在,则表明促销已过期;
	         *  .促销优先级库存可用量不存在,则表明促销已过期
	         *  .促销优先级库存可用量小于1,则表明促销商品已售完,切换正常优先级.
	         */
	        if (StringUtils.isBlank(priority)
	                || !cacheClient.exists(priceKey)
	                || !cacheClient.exists(priorityUsable)
	                || Long.parseLong(cacheClient.get(priorityUsable))<1){
	            //使用库存组指定优先级
	            priority = cacheClient.hget(groupKey,StorageConstants.IPass.McsParams.GROUP_SERIAL_HTAGE);
	            //库存组当前优先级库存可用量
	            priorityUsable = IPaasStorageUtils.genMcsPriorityUsableKey(tenantId,groupId,priority);
	        }
	        String usableNumStr = cacheClient.get(priorityUsable);
	        return StringUtils.isBlank(usableNumStr)?0:Long.parseLong(usableNumStr);
	    }
	    
	    /**
	     * 获取促销优先级
	     * @param cacheClient
	     * @param tenantId
	     * @param groupId
	     * @return
	     */
	    private static String getPromotionPriority(ICacheClient cacheClient,String tenantId,String groupId){
	        String serialsKey = IPaasStorageUtils.genMcsGroupSerialStartTimeKey(tenantId,groupId);
	        long nowTime = System.currentTimeMillis();
	        //使用优先级
	        String serial = "";
	        Set<String> serialSet = cacheClient.zrevrangeByScore(serialsKey,nowTime,0);
	        if (!CollectionUtil.isEmpty(serialSet)){
	            serial = serialSet.toArray(new String[0])[0];
	        }
	        return serial;
	    }
	    

}
