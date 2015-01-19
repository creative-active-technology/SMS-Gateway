/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.service.impl;

import static com.google.common.collect.Lists.newArrayList;
import com.inkubator.datacore.service.impl.IServiceImpl;
import com.inkubator.sms.gateway.dao.UserDao;
import com.inkubator.sms.gateway.dao.UserRoleDao;
import com.inkubator.sms.gateway.entity.SmsGatewayUser;
import com.inkubator.sms.gateway.entity.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author deni
 */
@Service(value = "userDetailsService")
@Lazy
public class UserDetailsServiceImpl extends IServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED, timeout = 30)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
         System.out.println("user detail service");
        if (userName == null || userName.trim().isEmpty()) {
            throw new UsernameNotFoundException("Empty username");
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Security verification for user '" + userName + "'");
        }
        SmsGatewayUser spiUser = this.userDao.getByUserIdOrEmail(userName);
        if (spiUser == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("User " + userName + " could not be found");
            }
            throw new UsernameNotFoundException("user " + userName + " could not be found");
        }else{
            System.out.println("ketemu kokk");
        }
//        List<SpiRole> spiRoles = new ArrayList<>();
        List<String> dataRole = new ArrayList<>();
        for (UserRole spiUserRole : userRoleDao.getByUserId(spiUser.getId())) {
//            spiRoles.add(spiUserRole.getSpiRole());
            dataRole.add(spiUserRole.getRole().getRoleName());
        }
        Collection<GrantedAuthority> grantedAuthorities = toGrantedAuthorities(dataRole);
        String password = spiUser.getPassword();
        Boolean isActive = Boolean.FALSE;
        Boolean isLock = Boolean.FALSE;
        Boolean isExired = Boolean.FALSE;

        if (spiUser.getIsActive() == 1) {
            isActive = Boolean.TRUE;
        }
        if (spiUser.getIsExpired() == 1) {
            isExired = Boolean.TRUE;
        }
        if (spiUser.getIsLock() == 1) {
            isLock = Boolean.TRUE;
        }
//        for (SpiRole spiRole : spiRoles) {
//            dataRole.add(spiRole.getRoleName());
//        }
         return new User(userName, password, isActive, true, !isExired, !isLock, grantedAuthorities);
    }

    private Collection<GrantedAuthority> toGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> result = newArrayList();
        for (String role : roles) {
            result.add(new SimpleGrantedAuthority(role));
        }
        return result;
    }
}