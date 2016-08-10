package com.ai.slp.order.api.demo.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.slp.order.api.demo.param.HelloParam;

/**
 * dubbo rest demo 示例
 *
 * Date: 2016年6月19日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author gucl
 */
@Path("/demo")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IDemoSV {
	
	/**
	 * hello 单个字符串参数
	 * @param name
	 * @return
	 * @author gucl
	 * @ApiDocMethod
	 * @ApiCode DEMO_0001
	 * @RestRelativeURL demo/hello
	 */
	@POST
	@Path("/hello")
	String hello(String name);
	
	/**
	 * helloParam 对象参数
	 * @param param
	 * @return
	 * @author gucl
	 * @ApiDocMethod
	 * @ApiCode DEMO_0002
	 * @RestRelativeURL demo/helloParam
	 */
	@POST
	@Path("/helloParam")
	String helloParam(HelloParam param);
	
	@POST
	@Path("/hellotest")
	String hellotest(String name,int age,String positon);
}
