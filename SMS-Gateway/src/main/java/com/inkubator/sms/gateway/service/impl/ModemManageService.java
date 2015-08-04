/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service.impl;

import com.inkubator.common.notification.model.SerialGateWay;
import com.inkubator.common.util.JsonConverter;
import com.inkubator.sms.gateway.web.model.ApprovalModel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.jms.JMSException;
import javax.jms.Session;
import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.AGateway.Protocols;
import org.smslib.GatewayException;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.IUSSDNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.USSDResponse;
import org.smslib.modem.SerialModemGateway;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author Deni Husni FR
 */
public class ModemManageService {

    private static final Logger LOGGER = Logger.getLogger(ModemManageService.class);
    private JsonConverter jsonConverter;
    private JmsTemplate jmsSenderSms;
    private static final Pattern CUSD_RESPONSE_PATTERN = Pattern.compile("\\s+\\+CUSD: (\\d+)(?:,\"(.*)\", ?\\d+)?\\s+", Pattern.DOTALL);

    public SerialModemGateway startServiceAndAddGateway(SerialGateWay gateway) {
        SerialModemGateway modemGateway = null;
        try {
            modemGateway = new SerialModemGateway(gateway.getModemId(), gateway.getComPort(), gateway.getBaudRate(), gateway.getManaufactur(), gateway.getModelName());
            modemGateway.setInbound(gateway.getInBound());
            modemGateway.setOutbound(gateway.getOutBound());
            modemGateway.setSimPin(gateway.getPinNumber());
            modemGateway.setSmscNumber(gateway.getSmscNumber());
            modemGateway.setOutbound(true);
            modemGateway.setInbound(true);
            modemGateway.setProtocol(Protocols.PDU);
//            modemGateway.getATHandler().setStorageLocations("SMME");
//            try {
            String resp = null;
//            try {
//                resp = modemGateway.sendCustomATCommand("AT+CUSD=1,\"*123#\",15\r");
//            } catch (TimeoutException | IOException | InterruptedException ex) {
//                java.util.logging.Logger.getLogger(ModemManageService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//                System.out.println(" Perintah nya " + resp);

            InboundNotification inboundNotification = new InboundNotification();
// Create the notification callback method for inbound voice calls.
            CallNotification callNotification = new CallNotification();
//Create the notification callback method for gateway statuses.
            GatewayStatusNotification statusNotification = new GatewayStatusNotification();
            OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
            Service.getInstance().setInboundMessageNotification(inboundNotification);
            Service.getInstance().setCallNotification(callNotification);
            Service.getInstance().setGatewayStatusNotification(statusNotification);
            Service.getInstance().setOrphanedMessageNotification(orphanedMessageNotification);
            Service.getInstance().setUSSDNotification(new USSDNotification());
            Service.getInstance().addGateway(modemGateway);

        } catch (GatewayException ex) {
            LOGGER.error(ex, ex);
        }
        return modemGateway;
    }

    public static class OutboundNotification implements IOutboundMessageNotification {

        @Override
        public void process(org.smslib.AGateway gateway, OutboundMessage msg) {
            LOGGER.info("Outbound handler called from Gateway " + gateway.getGatewayId());
            LOGGER.info(msg);
        }

    }

    public class InboundNotification implements IInboundMessageNotification {

        @Override
        public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg) {
            try {
                if (msgType == Message.MessageTypes.INBOUND) {
                    LOGGER.info(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
                } else if (msgType == Message.MessageTypes.STATUSREPORT) {
                    LOGGER.info(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
                }
                LOGGER.info("Pengirim" + msg.getOriginator());
                LOGGER.info("Isi SMS " + msg.getText());
                ApprovalModel approvalModel = new ApprovalModel();
                approvalModel.setSenderNumber(msg.getOriginator());
                approvalModel.setSmsContent(msg.getText());
                Service.getInstance().deleteMessage(msg);
                final String dataToSend = jsonConverter.getJson(approvalModel);
                LOGGER.info("ISI JSON " + dataToSend);
                jmsSenderSms.send(new MessageCreator() {
                    @Override
                    public javax.jms.Message createMessage(Session session)
                            throws JMSException {
                        return session.createTextMessage(dataToSend);
                    }
                });
                LOGGER.info("ISI JSON Terkirim");
//                approvalModel.setApproverNumberHp(msg.getOriginator());
//                Service.getInstance().deleteMessage(msg);//harus disini jika terjadi error parsing message tetap terdelere dan tidak menumpuk di kartu
//                approvalModel.setApproveCondition(st.nextToken());
//                approvalModel.setApprovalActivityNumber(st.nextToken());
//                approvalModel.setComentar(st.nextToken());
//                System.out.println(approvalModel);

            } catch (Exception ex) {
                LOGGER.error("ini errornya " + ex.getClass());

            }

        }
    }

    public class CallNotification implements ICallNotification {

        @Override
        public void process(AGateway gateway, String callerId) {
            System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    public class GatewayStatusNotification implements IGatewayStatusNotification {

        @Override
        public void process(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus) {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    public class OrphanedMessageNotification implements IOrphanedMessageNotification {

        @Override
        public boolean process(AGateway gateway, InboundMessage msg) {
            System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
            System.out.println(msg);
// Since we are just testing, return FALSE and keep the orphaned message part.
            return false;
        }
    }

    public class USSDNotification implements IUSSDNotification {

        @Override
        public void process(AGateway gateway, USSDResponse response) {
            System.out.println("USSD handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(response.getContent());
            System.out.println(response);
//            Matcher m = CUSD_RESPONSE_PATTERN.matcher(response.getRawResponse());
////            String type = m.group(1);
////            System.out.println(" Tyoenya " + type);
//            String nilai = m.group(2);
//            System.out.println("Nilainya "+nilai);
            
        }
    }

    public void setJsonConverter(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public void setJmsSenderSms(JmsTemplate jmsSenderSms) {
        this.jmsSenderSms = jmsSenderSms;
    }

}
