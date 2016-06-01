package com.ai.slp.order.api.orderlist.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderParams;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.ProdExtendInfoVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.constants.ResultCodeConstants;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderListSVImpl implements IOrderListSV {

	//@Autowired
	//private IOrdOrderBusiSV ordOrderBusiSV;

	@Override
	public QueryOrderListResponse queryOrderList(QueryOrderListRequest orderListRequest) throws BusinessException, SystemException {
		// TODO 等待实现
		// return ordOrderBusiSV.queryOrderList(orderListRequest);
		return getOrderListResponse();
	}

	/**
	 * 写死的数据模型，等待后期实现
	 * 
	 * @return
	 */
	private QueryOrderListResponse getOrderListResponse() {
		QueryOrderListResponse queryResponse = new QueryOrderListResponse();
		PageInfo<OrdOrderParams> pageInfo = new PageInfo<OrdOrderParams>();
		pageInfo.setCount(3);
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(5);
		List<OrdOrderParams> result = new LinkedList<OrdOrderParams>();
		String[] payStyleArray = new String[]{"11","111","111"}; 
		String[] payStyleNameArray = new String[]{"待支付","已支付","已支付"}; 
		for (int k = 0; k < 3; k++) {
			OrdOrderParams ordOrderParams1 = new OrdOrderParams();
			ordOrderParams1.setOrderId(10001L+k);
			ordOrderParams1.setBusiCode("1");// 1：正常单
			ordOrderParams1.setOrderType("100010");// 话费直充
			ordOrderParams1.setState(payStyleArray[k]);// 11 待支付
			ordOrderParams1.setStateName(payStyleNameArray[k]);
			ordOrderParams1.setPayStyle("21");
			ordOrderParams1.setPayStyleName("支付宝");
			ordOrderParams1.setOrderTime(DateUtil.getSysDate());
			ordOrderParams1.setTotalFee(3000L);
			ordOrderParams1.setDiscountFee(0L);
			ordOrderParams1.setAdjustFee(3000L);
			List<OrdProductVo> productList1 = new LinkedList<OrdProductVo>();
			String[] nameArray = new String[] { "中国联通手机充值100元", "中国移动手机充值100元", "中国电信手机充值100元" };
			String[] orgNameArray = new String[] { "中国联通", "中国移动", "中国电信" };
			for (int j = 0; j < 3; j++) {
				OrdProductVo ordProductVo1 = new OrdProductVo();
				ordProductVo1.setOrderId(10001L+k);
				ordProductVo1.setSkuId("100" + j);
				ordProductVo1.setProdName(nameArray[j]);
				ordProductVo1.setBuySum(10L);
				ordProductVo1.setSalePrice(100L);
				ordProductVo1.setTotalFee(1000L);
				ordProductVo1.setDiscountFee(0L);
				ordProductVo1.setAdjustFee(1000L);
				ordProductVo1.setBasicOrgName(orgNameArray[j]);
				ordProductVo1.setProvinceName("北京");
				ordProductVo1.setChargeFee("100元");
				ProductImage productImage1 = new ProductImage();
				productImage1.setExtension(".jpg");
				productImage1.setIdpsId("57454f50d601800009c0b0cf");
				ordProductVo1.setProductImage(productImage1);
				ordProductVo1.setImageUrl("http://10.1.245.8:18007/iPaas-IDPS/image/57454f50d601800009c0b0cf_75x75.jpg?userId=8EA4FD928D72469DA05D99004B260DF4&serviceId=IDPS001");
				List<ProdExtendInfoVo> prodExtendInfoVoList1 = new LinkedList<ProdExtendInfoVo>();
				for (int i = 0; i < 10; i++) {
					ProdExtendInfoVo prodExtendInfoVo = new ProdExtendInfoVo();
					prodExtendInfoVo.setProdExtendInfoValue("1832233400" + i);
					prodExtendInfoVoList1.add(prodExtendInfoVo);
				}
				ordProductVo1.setProdExtendInfoVoList(prodExtendInfoVoList1);
				productList1.add(ordProductVo1);
			}
			ordOrderParams1.setProductList(productList1);
			result.add(ordOrderParams1);
		}
		pageInfo.setResult(result);
		queryResponse.setPageInfo(pageInfo);
		ResponseHeader responseHeader = new ResponseHeader(true, ResultCodeConstants.SUCCESS_CODE, "查询成功");
		queryResponse.setResponseHeader(responseHeader);
		return queryResponse;
	}

}
