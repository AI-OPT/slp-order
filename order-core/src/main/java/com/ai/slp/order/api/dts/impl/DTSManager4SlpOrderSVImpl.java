package com.ai.slp.order.api.dts.impl;

import java.util.List;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.dts.factory.DTSSchedulerFactory;
import com.ai.opt.sdk.dts.service.interfaces.IDTSManagerSV;
import com.ai.opt.sdk.dts.service.param.TaskCond;
import com.ai.opt.sdk.dts.service.param.TaskData;
import com.alibaba.dubbo.config.annotation.Service;

@Service(group = "dts-slp-order")
public class DTSManager4SlpOrderSVImpl implements IDTSManagerSV {

    @Override
    public void addOrUpdateTask(TaskData arg0) throws BusinessException, SystemException {
        DTSSchedulerFactory.addOrUpdateTask(arg0);
    }

    @Override
    public void deleteTask(TaskData arg0) throws BusinessException, SystemException {
        DTSSchedulerFactory.deleteTask(arg0);
    }

    @Override
    public TaskData getTaskData(TaskCond taskCond) throws BusinessException, SystemException {
        return DTSSchedulerFactory.getTaskData(taskCond.getSchedulerName(), taskCond.getJobName(),
                taskCond.getJobGroup());
    }

    @Override
    public List<TaskData> getAllTasks(String arg0) {
        return DTSSchedulerFactory.getAllTasks(arg0);
    }

}
