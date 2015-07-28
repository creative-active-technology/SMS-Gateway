/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inkubator.common.notification.model.SMSSend;
import com.inkubator.common.util.JsonConverter;
import com.inkubator.datacore.service.impl.IServiceImpl;
import com.inkubator.sms.gateway.SMSGATEWAY;
import com.inkubator.sms.gateway.dao.TaskDefinitionDao;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import com.inkubator.sms.gateway.service.SchedullerSendingSmsService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
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
    @Autowired
    private JmsTemplate jmsTemplateSMS;
    @Autowired
    private JsonConverter jsonConverter;

    @Override
    @Scheduled(cron = "${cron.sending.sms.scheduller}")
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendScheduleSms() throws Exception {
        List<TaskDefinition> allDataHaveToSendBySms = taskDefinitionDao.getAllData();

        for (TaskDefinition taskDefinition : allDataHaveToSendBySms) {
            if (taskDefinition.getScheduleType().equals(SMSGATEWAY.SMS_REPEAT)) {
                if (SMSGATEWAY.EVERY_DAY.equals(taskDefinition.getIsRepeatOnCondition())) {
                    if (new Date().after(taskDefinition.getDate()) || new Date().equals(taskDefinition.getDate())) {
                        String waktuSekarang = new SimpleDateFormat("HH:mm:ss").format(new Date());
                        String waktukirim = new SimpleDateFormat("HH:mm:ss").format(taskDefinition.getTime());
                        if (waktuSekarang.equals(waktukirim)) {
                            sendingSms(taskDefinition);
                        }
                    }
                }
                if (SMSGATEWAY.EVERY_WEEK.equals(taskDefinition.getIsRepeatOnCondition())) {
                    if (new Date().after(taskDefinition.getDate()) || new Date().equals(taskDefinition.getDate())) {
                        String hariSekarang = new SimpleDateFormat("EEEE").format(new Date());
                        System.out.println(" hari sekarngan ad" + hariSekarang);
                        String hariPengiriman = new SimpleDateFormat("EEEE").format(taskDefinition.getDate());
                        System.out.println(" hari pengirimana ad" + hariPengiriman);
                        if (hariSekarang.equals(hariPengiriman)) {
                            String waktuSekarang = new SimpleDateFormat("HH:mm:ss").format(new Date());
                            String waktukirim = new SimpleDateFormat("HH:mm:ss").format(taskDefinition.getTime());
                            if (waktuSekarang.equals(waktukirim)) {
                                sendingSms(taskDefinition);
                            }
                        }
                    }

                }
                if (SMSGATEWAY.EVERY_MONTH.equals(taskDefinition.getIsRepeatOnCondition())) {
                    if (new Date().after(taskDefinition.getDate()) || new Date().equals(taskDefinition.getDate())) {
                        String tanggalSekarng = new SimpleDateFormat("dd").format(new Date());
                        String tanggalPengiriman = new SimpleDateFormat("dd").format(taskDefinition.getDate());
                        if (tanggalSekarng.equals(tanggalPengiriman)) {
                            String waktuSekarang = new SimpleDateFormat("HH:mm:ss").format(new Date());
                            String waktukirim = new SimpleDateFormat("HH:mm:ss").format(taskDefinition.getTime());
                            if (waktuSekarang.equals(waktukirim)) {
                                sendingSms(taskDefinition);
                            }
                        }
                    }
                }

            }
            if (SMSGATEWAY.SMS_ONCE.equals(taskDefinition.getScheduleType())) {
                if (new Date().equals(taskDefinition.getDate())) {
                    String waktuSekarang = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    String waktukirim = new SimpleDateFormat("HH:mm:ss").format(taskDefinition.getTime());
                    if (waktuSekarang.equals(waktukirim)) {
                        sendingSms(taskDefinition);
                    }
                }
            }

        }
    }

    private void sendingSms(TaskDefinition taskDefinition) {
        final SMSSend sendModel = new SMSSend();
        Gson gson = new GsonBuilder().create();
        TypeToken<List<String>> token = new TypeToken<List<String>>() {
        };
        List<String> toLoop = gson.fromJson(taskDefinition.getDestination(), token.getType());
        for (String notlp : toLoop) {
            sendModel.setPricePerSms(taskDefinition.getModemDefinition().getPricePerSms());
            sendModel.setContent(taskDefinition.getSmsContent());
            sendModel.setFrom("System");
            sendModel.setDestination(notlp);
            sendModel.setModemId(taskDefinition.getModemDefinition().getModemId());
            this.jmsTemplateSMS.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session)
                        throws JMSException {
                    return session.createTextMessage(jsonConverter.getJson(sendModel));
                }
            });

        }
    }
}
