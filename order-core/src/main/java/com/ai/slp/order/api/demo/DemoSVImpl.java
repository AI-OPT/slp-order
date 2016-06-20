package com.ai.slp.order.api.demo;

import org.springframework.stereotype.Component;

import com.ai.slp.order.api.demo.interfaces.IDemoSV;
import com.ai.slp.order.api.demo.param.HelloParam;
import com.alibaba.dubbo.config.annotation.Service;

@Service
@Component
public class DemoSVImpl implements IDemoSV {

	@Override
	public String hello(String name) {
		return "Hello,"+name;
	}

	@Override
	public String helloParam(HelloParam param) {
		String name=(param==null?"param world":param.getName());
		return "Hello,"+name;
	}

	@Override
	public String hellotest(String name, int age, String positon) {
		String realName=(name==null?"world":name);
		String realPositon=(positon==null?"Manager":positon);
		return "Hello,["+realName+","+age+","+realPositon+"]";
	}

}
