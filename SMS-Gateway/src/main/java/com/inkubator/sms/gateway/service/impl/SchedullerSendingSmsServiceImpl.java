/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service.impl;

import com.inkubator.datacore.service.impl.IServiceImpl;
import com.inkubator.sms.gateway.dao.TaskDefinitionDao;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import com.inkubator.sms.gateway.service.SchedullerSendingSmsService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author deni.fahri
 */
public class SchedullerSendingSmsServiceImpl extends IServiceImpl implements SchedullerSendingSmsService {

    @Autowired
    private TaskDefinitionDao taskDefinitionDao;

    @Override
    @Scheduled(cron = "${cron.sending.sms.scheduller}")
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendScheduleSms() throws Exception {
       List<TaskDefinition>allDataHaveToSendBySms=taskDefinitionDao.getAllData();
        for (TaskDefinition allDataHaveToSendBySm : allDataHaveToSendBySms) {
            System.out.println(" Nilainnyayya "+allDataHaveToSendBySm.getName());
        }
    }

}
