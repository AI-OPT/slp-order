package com.ai.slp.order.dubbo;

import com.ai.opt.sdk.appserver.DubboServiceStart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class DubboStartTest {
    @Test
    public void testDubboStart(){
        DubboServiceStart.main(null);
    }
}
