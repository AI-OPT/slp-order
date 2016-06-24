package com.ai.slp.order.api.dts;

import com.ai.opt.sdk.dts.factory.DTSSchedulerFactory;
import com.ai.opt.sdk.dts.service.param.TaskData;
import com.ai.slp.order.distributedtask.NoPayOrderAutoCancelTask;

public class DTSAdd {

    public static void main(String[] args) throws Exception {
        TaskData job = new TaskData();
        job.setSchedulerName("slp-order-nopaycancel");
        job.setJobStatus("1");
        job.setImplClassName(NoPayOrderAutoCancelTask.class.getName());
        job.setDesc("ָ测试任务");
        job.setCronExpression("0/5 * * * * ?");
        job.setJobGroup("dts-slp-order");
        job.setJobName("测试1");
        job.getEnvVars().put("var1", "1");
        DTSSchedulerFactory.addOrUpdateTask(job);
        // DTSSchedulerFactory.deleteTask(job);
        // DTSSchedulerFactory.pauseTask(job);
        // DTSSchedulerFactory.getAllTasks("runner-test");
        DTSSchedulerFactory.resumeJob(job);
        System.exit(-1);
        System.out.println("OK");
    }

}
