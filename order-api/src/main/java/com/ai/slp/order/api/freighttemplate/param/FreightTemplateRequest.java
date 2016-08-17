package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;


/**
 * 运费模版请求参数 Date: 2016年8月3日 <br>
 * @author caofz
 *
 */
public class FreightTemplateRequest implements Serializable{

		private static final long serialVersionUID = 1L;

		/**
		 * 运费模版信息
		 */
		@NotNull(message="运费模版信息不能为空")
		private FreightTemplateInfo freightTemplateInfo;
		
		/**
		 * 运费模版明细信息
		 */
		private List<FreightTemplateProdInfo> freightTemplateProdInfos;

		public FreightTemplateInfo getFreightTemplateInfo() {
			return freightTemplateInfo;
		}

		public void setFreightTemplateInfo(FreightTemplateInfo freightTemplateInfo) {
			this.freightTemplateInfo = freightTemplateInfo;
		}

		public List<FreightTemplateProdInfo> getFreightTemplateProdInfos() {
			return freightTemplateProdInfos;
		}

		public void setFreightTemplateProdInfos(List<FreightTemplateProdInfo> freightTemplateProdInfos) {
			this.freightTemplateProdInfos = freightTemplateProdInfos;
		}
		
}
