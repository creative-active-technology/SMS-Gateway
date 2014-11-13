/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.dao.impl;

import com.inkubator.datacore.dao.impl.IDAOImpl;
import com.inkubator.sms.gateway.dao.TaskDefinitionDao;
import com.inkubator.sms.gateway.entity.TaskDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Deni Husni FR
 */
@Repository(value = "taskDefinitionDao")
@Lazy
public class TaskDefinitionDaoImpl extends IDAOImpl<TaskDefinition> implements TaskDefinitionDao {

    @Override
    public Class<TaskDefinition> getEntityClass() {
        return TaskDefinition.class;
    }

}
