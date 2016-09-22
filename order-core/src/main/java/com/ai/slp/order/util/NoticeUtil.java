package com.ai.slp.order.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import com.ylink.itfin.certificate.SecurityUtil;
import com.ylink.upp.base.oxm.XmlBodyEntity;
import com.ylink.upp.base.oxm.util.Dom4jHelper;
import com.ylink.upp.base.oxm.util.HandlerMsgUtil;
import com.ylink.upp.base.oxm.util.HeaderBean;
import com.ylink.upp.base.oxm.util.OxmHandler;
public class NoticeUtil {
	
		@Autowired
		private static OxmHandler oxmHandler;
		
		public static String sendHttpPost(String url, Map<String, String> param, String charset) throws Exception {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
			try {
				HttpPost post = new HttpPost(url);
				post.setConfig(requestConfig);
				if (!CollectionUtils.isEmpty(param)) {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					for (String key : param.keySet()) {
						params.add(new BasicNameValuePair(key, param.get(key)));
					}
					HttpEntity fromEntity = new UrlEncodedFormEntity(params, charset);
					post.setEntity(fromEntity);
				}
				HttpResponse response = httpClient.execute(post);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return EntityUtils.toString(response.getEntity(), charset);
				} else if (302 == response.getStatusLine().getStatusCode()) {
					Header hs = response.getFirstHeader("Location");
					if (hs != null) {
						String lo = hs.getValue();
						if (!checkUrl(lo)) {
							lo = findUrl(post.getURI().toASCIIString()) + lo;
						}
						return sendHttpPostGet(lo, null, charset);
					}
					String result = EntityUtils.toString(response.getEntity(), charset);
					return result;
				} else {
					throw new Exception("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
				}
			} finally {
				if (null != httpClient) {
					httpClient.close();
				}
			}
		}

		public static String sendHttpPostGet(String url, Map<String, String> data, String charset) throws Exception {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
			try {
				url = url.replaceFirst("^http://|^http://", "");
				URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(url);
				if (!CollectionUtils.isEmpty(data)) {
					for (String key : data.keySet()) {
						uriBuilder.setParameter(key, data.get(key));
					}
				}
				HttpGet httpGet = new HttpGet(uriBuilder.build());
				httpGet.setConfig(requestConfig);
				HttpResponse response = httpClient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					return EntityUtils.toString(response.getEntity(), charset);
				} else if (302 == response.getStatusLine().getStatusCode()) {
					Header hs = response.getFirstHeader("Location");
					if (hs != null) {
						String lo = hs.getValue();
						if (!checkUrl(lo)) {
							lo = findUrl(httpGet.getURI().toASCIIString()) + lo;
						}
						return sendHttpPostGet(lo, null, charset);
					}
					String result = EntityUtils.toString(response.getEntity(), charset);
					return result;
				} else {
					throw new Exception("调用URL地址通讯失败,失败状态：" + response.getStatusLine().getStatusCode());
				}
			} finally {
				if (null != httpClient) {
					httpClient.close();
				}
			}
		}

		private static boolean checkUrl(String url) {
			Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
			Matcher m = p.matcher(url);
			return m.find();
		}

		protected static String findUrl(String url) {
			Pattern p = Pattern.compile("^(http|https)://.+?(?=/)");
			Matcher m = p.matcher(url);
			if (m.find()) {
				return m.group();
			}
			return null;
		}

		/**
		 * 拼装报文头
		 * 
		 * @return
		 */
		public static String initMsgHeader(String merNo, String tranType) {
			StringBuffer buffer = new StringBuffer("{H:01");
			buffer.append(merNo);
			buffer.append("1000000000000000");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			buffer.append(dateFormat.format(new Date(System.currentTimeMillis())));
			buffer.append(tranType);
			buffer.append("}");
			return buffer.toString();
		}

		/**
		 * 加签
		 * 
		 * @param xmlMsg
		 * @return
		 * @throws Exception
		 */
		public static String sign(String xmlMsg) throws Exception {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource pfxResource = resourceLoader.getResource("classpath:CO20160900000009.pfx"); // 支付公钥加签
			InputStream in = new FileInputStream(pfxResource.getFile());
			byte[] pfxByte = IOUtils.toByteArray(in);
			String sign = SecurityUtil.pfxWithSign(pfxByte, xmlMsg, "111111");
			return sign;
		}

		/**
		 * 验签
		 * 
		 * @param xmlMsg
		 * @param sign
		 * @return
		 * @throws Exception
		 */
		public static boolean verify(String xmlMsg, String sign) throws Exception {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			Resource pfxResource = resourceLoader.getResource("classpath:mobile.cer"); // 商户私钥解签
			InputStream in = new FileInputStream(pfxResource.getFile());
			byte[] cerByte = IOUtils.toByteArray(in);
			;
			return SecurityUtil.verify(cerByte, xmlMsg, sign);
		}
		
		public static XmlBodyEntity receiveMsg(String msgHeader, String xmlMsg, String sign) {
			try {
				boolean verify = verify(xmlMsg, sign);
				if (!verify) {
					System.out.println("验签失败");
				}
				HeaderBean headerBean = new HeaderBean();
				HandlerMsgUtil.conversion(msgHeader, headerBean);
				xmlMsg = Dom4jHelper.addNamespace(xmlMsg, headerBean.getMesgType(), "UTF-8");
				return (XmlBodyEntity) oxmHandler.unmarshaller(xmlMsg);
			} catch (Exception e) {
				System.out.println("接收数据时发生异常，错误信息为:" + e.getMessage());
				throw new RuntimeException(e);
			}

		}

	}

