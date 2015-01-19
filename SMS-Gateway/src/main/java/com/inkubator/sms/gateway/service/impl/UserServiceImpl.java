/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service.impl;

import com.inkubator.common.util.AESUtil;
import com.inkubator.common.util.HashingUtils;
import com.inkubator.common.util.JsonConverter;
import com.inkubator.common.util.RandomNumberUtil;
import com.inkubator.datacore.service.impl.IServiceImpl;
import com.inkubator.securitycore.util.UserInfoUtil;
import com.inkubator.sms.gateway.SMSGATEWAY;
import com.inkubator.sms.gateway.dao.PasswordHistoryDao;
import com.inkubator.sms.gateway.dao.UserDao;
import com.inkubator.sms.gateway.dao.UserRoleDao;
import com.inkubator.sms.gateway.entity.PasswordHistory;
import com.inkubator.sms.gateway.entity.Role;
import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import com.inkubator.sms.gateway.entity.UserRole;
import com.inkubator.sms.gateway.service.UserService;
import com.inkubator.webcore.util.FacesUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author deni
 */
@Service(value = "userService")
@Lazy
public class UserServiceImpl extends IServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JsonConverter jsonConverter;
    @Autowired
    private PasswordHistoryDao passwordHistoryDao;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, timeout = 50)
    public List<SmsGatewayUser> getAllByFullTextService(String parameter, int minResult, int maxResult, Order order) throws Exception {
        return userDao.getAllByFullTextService(parameter, minResult, maxResult, order);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, timeout = 50)
    public Integer getTotalByFullTextService(String parameter) throws Exception {
        return userDao.getTotalByFullTextService(parameter);
    }

    @Override
    public SmsGatewayUser getEntiyByPK(String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntiyByPK(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, timeout = 30)
    public SmsGatewayUser getEntiyByPK(Long id) throws Exception {
        return userDao.getEntiyByPK(id);
    }

    @Override
    public void save(SmsGatewayUser entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(SmsGatewayUser entity) throws Exception {
        SmsGatewayUser update = userDao.getEntiyByPK(entity.getId());
        update.getUserRoles().clear();
        update.setRealName(entity.getRealName());
        update.setUserId(entity.getUserId());
        update.setPhoneNumber(entity.getPhoneNumber());
        update.setEmailAddress(entity.getEmailAddress());
        update.setIsActive(entity.getIsActive());
        update.setIsExpired(entity.getIsExpired());
        update.setIsLock(entity.getIsLock());
        userDao.saveAndMerge(update);
        Set<UserRole> dataToSave = entity.getUserRoles();
        for (UserRole userRole : dataToSave) {
            userRole.setUser(update);
            this.userRoleDao.save(userRole);
        }
    }

    @Override
    public void saveOrUpdate(SmsGatewayUser enntity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser saveData(SmsGatewayUser entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser updateData(SmsGatewayUser entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser saveOrUpdateData(SmsGatewayUser entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(String id, Integer isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(String id, Byte isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(String id, Boolean isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(Integer id, Integer isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(Integer id, Byte isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(Integer id, Boolean isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(Long id, Integer isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(Long id, Byte isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SmsGatewayUser getEntityByPkIsActive(Long id, Boolean isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(SmsGatewayUser entity) throws Exception {
        userDao.delete(entity);
    }

    @Override
    public void softDelete(SmsGatewayUser entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getTotalData() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getTotalDataIsActive(Boolean isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getTotalDataIsActive(Integer isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getTotalDataIsActive(Byte isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllData() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllData(Boolean isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllData(Integer isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllData(Byte isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllDataPageAble(int firstResult, int maxResults, Order order) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllDataPageAbleIsActive(int firstResult, int maxResults, Order order, Boolean isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllDataPageAbleIsActive(int firstResult, int maxResults, Order order, Integer isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SmsGatewayUser> getAllDataPageAbleIsActive(int firstResult, int maxResults, Order order, Byte isActive) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveAndNotification(SmsGatewayUser user) throws Exception {
        String pass = user.getPassword();
        final PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setId(Long.parseLong(RandomNumberUtil.getRandomNumber(12)));
        passwordHistory.setPassword(AESUtil.getAESEncription(user.getPassword(), SMSGATEWAY.KEYVALUE, SMSGATEWAY.AES_ALGO));
        user.setCreatedBy(UserInfoUtil.getUserName());
        user.setPassword(HashingUtils.getHashSHA256(user.getPassword()));
        user.setCreatedOn(new Date());
        userDao.save(user);
        passwordHistory.setEmailNotification(SMSGATEWAY.EMAIL_NOTIFICATION_NOT_YET_SEND);
        passwordHistory.setSmsNotification(SMSGATEWAY.SMA_NOTIFICATION_NOT_SEND);
        passwordHistory.setEmailAddress(user.getEmailAddress());
        passwordHistory.setRequestType(SMSGATEWAY.USER_NEW);
        passwordHistory.setPhoneNumber(user.getPhoneNumber());
        passwordHistory.setRealName(user.getRealName());
        passwordHistory.setUserName(user.getUserId());
        passwordHistory.setLocalId(FacesUtil.getSessionAttribute(SMSGATEWAY.BAHASA_ACTIVE).toString());
        passwordHistory.setCreatedBy(UserInfoUtil.getUserName());
        passwordHistory.setCreatedOn(new Date());
        List<String> dataRole = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        for (UserRole userRole : userRoleDao.getByUserId(user.getId())) {
            roles.add(userRole.getRole());
        }

        for (Role role : roles) {
            dataRole.add(role.getRoleName());
        }

        passwordHistory.setListRole(jsonConverter.getJson(dataRole.toArray(new String[dataRole.size()])));

        this.passwordHistoryDao.save(passwordHistory);
        this.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session)
                    throws JMSException {
                return session.createTextMessage(jsonConverter.getJson(passwordHistory));
            }
        });
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, timeout = 50)
    public SmsGatewayUser getEntityByPkWithDetail(Long id) throws Exception {
        SmsGatewayUser user = userDao.getEntiyByPK(id);
        List<Role> roles = new ArrayList<>();
        for (UserRole userRole : this.userRoleDao.getByUserId(id)) {
            roles.add(userRole.getRole());
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ, propagation = Propagation.SUPPORTS, timeout = 50)
    public SmsGatewayUser getByEmailAddressInNotLock(String emailAddress) throws Exception {
        return userDao.getByEmailAddressInNotLock(emailAddress);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void resetPassword(SmsGatewayUser user) throws Exception {
        SmsGatewayUser smsGatewayUser = this.userDao.getEntiyByPK(user.getId());
        smsGatewayUser.setPassword(HashingUtils.getHashSHA256(user.getPassword()));
        smsGatewayUser.setUpdatedBy(SMSGATEWAY.INKUBA_SYSTEM);
        smsGatewayUser.setUpdatedOn(new Date());
        this.userDao.update(smsGatewayUser);
        final PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setId(Long.parseLong(RandomNumberUtil.getRandomNumber(9)));
        passwordHistory.setCreatedBy(SMSGATEWAY.INKUBA_SYSTEM);
        passwordHistory.setCreatedOn(new Date());
        passwordHistory.setEmailAddress(user.getEmailAddress());
        passwordHistory.setEmailNotification(0);
        passwordHistory.setRequestType(SMSGATEWAY.USER_RESET);
        passwordHistory.setSmsNotification(-1);
        passwordHistory.setRealName(user.getRealName());
        passwordHistory.setPhoneNumber(user.getPhoneNumber());
        passwordHistory.setPassword(AESUtil.getAESEncription(user.getPassword(), SMSGATEWAY.KEYVALUE, SMSGATEWAY.AES_ALGO));
        passwordHistory.setUserName(user.getUserId());
        passwordHistory.setLocalId("en");
        List<String> roleNames = new ArrayList<>();
        for (UserRole userRole : userRoleDao.getByUserId(user.getId())) {
            roleNames.add(userRole.getRole().getRoleName());
        }
        passwordHistory.setListRole(jsonConverter.getJson(roleNames.toArray(new String[roleNames.size()])));
        this.passwordHistoryDao.save(passwordHistory);
        //send messaging, for processing sending email
        this.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session)
                    throws JMSException {
                return session.createTextMessage(jsonConverter.getJson(passwordHistory));
            }
        });
    }

}
