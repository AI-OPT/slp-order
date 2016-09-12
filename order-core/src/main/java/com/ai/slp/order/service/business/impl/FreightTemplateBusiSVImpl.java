package com.ai.slp.order.service.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.paas.ipaas.util.StringUtil;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateDeleteRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateInfo;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateProdInfo;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateProdVo;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateUpdateRequest;
import com.ai.slp.order.api.freighttemplate.param.FreightTemplateVo;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateRequest;
import com.ai.slp.order.api.freighttemplate.param.QueryFreightTemplateResponse;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.dao.mapper.bo.FreightTemplate;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateCriteria;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProd;
import com.ai.slp.order.dao.mapper.bo.FreightTemplateProdCriteria;
import com.ai.slp.order.service.atom.interfaces.IFreightTemplateAtomSV;
import com.ai.slp.order.service.atom.interfaces.IFreightTemplateProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IFreightTemplateBusiSV;
import com.ai.slp.order.util.SequenceUtil;

@Service
@Transactional
public class FreightTemplateBusiSVImpl implements IFreightTemplateBusiSV {
	
	private static final Logger logger =LoggerFactory.getLogger(OrdOrderApiTradeBusiSVImpl.class);
	
	@Autowired
	private IFreightTemplateAtomSV freightTemplateAtomSV;
	
	@Autowired
	private IFreightTemplateProdAtomSV freightTemplateProdAtomSV;

	@Override
	public void add(FreightTemplateRequest request) throws BusinessException, SystemException {
		/* 1.生成模版id*/
		String templateId = SequenceUtil.createTemplateId(); 
		/* 2.创建运费模版信息*/
		this.createFreightTemplate(request, templateId);
		/* 3.创建运费模版明细信息*/
		this.createFreightTemplateProd(request, templateId);
	}
	
	
	@Override
	public QueryFreightTemplateResponse query(QueryFreightTemplateRequest request)
			throws BusinessException, SystemException {
		QueryFreightTemplateResponse response=new QueryFreightTemplateResponse();
		PageInfo<FreightTemplateVo> pageInfo = new PageInfo<FreightTemplateVo>();
		/* 1.参数校验*/
		if(StringUtil.isBlank(request.getSupplierId())) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "销售商id为空");
		}
		FreightTemplateCriteria example=new FreightTemplateCriteria();
		FreightTemplateCriteria.Criteria criteria = example.createCriteria();
		criteria.andSupplierIdEqualTo(request.getSupplierId());
		/* 2.查询运费模版总的个数*/
		int count=freightTemplateAtomSV.countByExample(example);
		Integer pageSize = request.getPageSize();
		example.setLimitStart((request.getPageNo()-1)*pageSize);
		example.setLimitEnd(pageSize);
		example.setOrderByClause("TIME DESC");
		/* 3.获取运费模版信息*/
		List<FreightTemplate> freightTemplates = freightTemplateAtomSV.selectByExample(example);
		if(CollectionUtil.isEmpty(freightTemplates)) {
			logger.warn("未能找到相应的运费模版信息,[销售商id:"+request.getSupplierId()+"]");
			throw new BusinessException("", "未能找到相应的运费模版信息,[销售商id:"+request.getSupplierId()+"]");
		}
		List<FreightTemplateVo> freightTemplateVos=new ArrayList<FreightTemplateVo>();
		/* 4.设值*/
		for (FreightTemplate freightTemplate : freightTemplates) {
			FreightTemplateVo freightTemplateVo=new FreightTemplateVo();
			freightTemplateVo.setTemplateId(freightTemplate.getTemplateId());
			freightTemplateVo.setTemplateName(freightTemplate.getTemplateName());
			freightTemplateVo.setValuationType(freightTemplate.getValuationType());
			freightTemplateVo.setTime(freightTemplate.getTime());
			FreightTemplateProdCriteria exampleProd=new FreightTemplateProdCriteria();
			FreightTemplateProdCriteria.Criteria criteriaProd = exampleProd.createCriteria();
			criteriaProd.andTemplateIdEqualTo(freightTemplate.getTemplateId());
			/* 5.获取运费模版明细信息*/
			List<FreightTemplateProd> ftProds = freightTemplateProdAtomSV.selectByExample(exampleProd);
			List<FreightTemplateProdVo> templateProdList=new ArrayList<FreightTemplateProdVo>();
			for (FreightTemplateProd source : ftProds) {
				FreightTemplateProdVo target= new FreightTemplateProdVo();
		        BeanUtils.copyProperties(target , source);
		        templateProdList.add(target);
		     }
			freightTemplateVo.setTemplateProdList(templateProdList);
			freightTemplateVos.add(freightTemplateVo);
		}
		pageInfo.setPageNo(request.getPageNo());
		pageInfo.setPageSize(pageSize);
		pageInfo.setCount(count);
		pageInfo.setResult(freightTemplateVos);
		response.setPageInfo(pageInfo);
		return response;
	}
	
	
	/**
	 * 创建运费模版信息
	 * @param request
	 * @param templateId
	 * @author caofz
	 * @ApiDocMethod
	 */
	private void createFreightTemplate(FreightTemplateRequest request,String templateId) {
		logger.debug("开始处理运费模版主表[templateId:" + templateId+"]资料信息...");
		/* 1.获取运费模版信息*/
		FreightTemplateInfo freightTemplateInfo = request.getFreightTemplateInfo();
		if(freightTemplateInfo==null) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "运费模版信息为空");
		}
		FreightTemplate freightTemplate=new FreightTemplate();
		freightTemplate.setTemplateId(templateId);
		freightTemplate.setTemplateName(freightTemplateInfo.getTemplateName());
		freightTemplate.setTemplateType(freightTemplateInfo.getTemplateType());
		freightTemplate.setSupplierId(freightTemplateInfo.getSupplierId());
		freightTemplate.setLogisticsCompanyId(freightTemplateInfo.getLogisticsCompanyId());
		freightTemplate.setIsFree(freightTemplateInfo.getIsFree());
		freightTemplate.setIsTermFree(freightTemplateInfo.getIsTermFree());
		freightTemplate.setValuationType(freightTemplateInfo.getValuationType());
		freightTemplate.setTime(DateUtil.getSysDate());
		/* 2.保存运费模版信息*/
		freightTemplateAtomSV.insertSelective(freightTemplate);
	}
	
	/**
	 * 创建运费模版明细信息
	 * @param request
	 * @param templateId
	 * @author caofz
	 * @ApiDocMethod
	 */
	private void createFreightTemplateProd(FreightTemplateRequest request,String templateId) {
		logger.debug("开始处理运费模版明细表[templateId:"+templateId+"]信息...");
		/* 1.获取运费模版明细信息*/
		List<FreightTemplateProdInfo> freightTemplateProdInfos = request.getFreightTemplateProdInfos();
		if(!CollectionUtil.isEmpty(freightTemplateProdInfos)) {
			for (FreightTemplateProdInfo ftProdInfo : freightTemplateProdInfos) {
				Long regionId = SequenceUtil.createRegionId();
				FreightTemplateProd ftProd=new FreightTemplateProd();
				ftProd.setRegionId(String.valueOf(regionId));
				ftProd.setTemplateId(templateId);
				ftProd.setTransportAddress(ftProdInfo.getTransportAddress());
				ftProd.setFirstNumber(ftProdInfo.getFirstNumber());
				ftProd.setFirstNum(ftProdInfo.getFirstNum()*1000); //元转厘
				ftProd.setPieceNumber(ftProdInfo.getPieceNumber());
				ftProd.setPieceNum(ftProdInfo.getPieceNum()*1000);
				/* 2.保存运费模版明细信息*/
				freightTemplateProdAtomSV.insertSelective(ftProd);
			}
		}
	}


	@Override
	public void update(FreightTemplateUpdateRequest request) throws BusinessException, SystemException {
		/* 1.更新运费模版信息*/
		String templateId = request.getTemplateId();
		FreightTemplate freightTemplate = freightTemplateAtomSV.selectByPrimaryKey(templateId);
		if(freightTemplate==null) {
			logger.error("未查询到指定的运费模版信息[模版id:"+templateId+"]");
			throw new BusinessException("", "未查询到指定的运费模版信息[模版id:"+templateId+"]");
		}
		FreightTemplateInfo freightTemplateInfo = request.getFreightTemplateInfo();
		BeanUtils.copyProperties(freightTemplate, freightTemplateInfo);
		freightTemplate.setTime(DateUtil.getSysDate());
		freightTemplateAtomSV.updateByPrimaryKeySelective(freightTemplate);
		/* 2.更新运费模版明细信息*/
		List<FreightTemplateProdInfo> ftProdInfos = request.getFreightTemplateProdInfos();
		for (FreightTemplateProdInfo ftProdInfo : ftProdInfos) {
			FreightTemplateProd ftSource = freightTemplateProdAtomSV.selectByPrimaryKey(ftProdInfo.getRegionId());
			if(ftSource==null) {
				logger.error("未查询到指定的运费模版明细信息[对应区域id:"+ftProdInfo.getRegionId()+"]");
				throw new BusinessException("", "未查询到指定的运费模版明细信息[对应区域id:"+ftProdInfo.getRegionId()+"]");
			}else if (ftSource!=null&&!templateId.equals(ftSource.getRegionId())) {
				logger.error("对应区域id和模版id没对应上,[对应区域id:"+ftProdInfo.getRegionId()+",模版id:"+templateId+"]");
				throw new BusinessException("", "未查询到指定的运费模版明细信息[对应区域id:"+ftProdInfo.getRegionId()+",模版id:"+templateId+"]");
			}
			BeanUtils.copyProperties(ftSource,ftProdInfo);
			freightTemplateProdAtomSV.updateByPrimaryKeySelective(ftSource);
		}
	}

	@Override
	public void delete(FreightTemplateDeleteRequest request) {
		String templateId = request.getTemplateId();
		/* 1.删除运费模版信息*/
		freightTemplateAtomSV.deleteByPrimaryKey(templateId);
		FreightTemplateProdCriteria example=new FreightTemplateProdCriteria();
		FreightTemplateProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTemplateIdEqualTo(request.getTemplateId());
		List<FreightTemplateProd> ftProds = freightTemplateProdAtomSV.selectByExample(example);
		/* 2.删除运费模版明细信息*/
		for (FreightTemplateProd freightTemplateProd : ftProds) {
			freightTemplateProdAtomSV.deleteByPrimaryKey(freightTemplateProd.getRegionId());
		}
	}
}
