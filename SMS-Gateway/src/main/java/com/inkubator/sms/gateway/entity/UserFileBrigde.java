/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inkubator.sms.gateway.entity;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

/**
 *
 * @author deni.fahri
 */
public class UserFileBrigde implements TwoWayFieldBridge {

    @Override
    public Object get(String string, Document dcmnt) {
        UserRoleId id = new UserRoleId();
        id.setRoleId(Long.parseLong(dcmnt.get("userId")));
        id.setUserId(Long.parseLong(dcmnt.get("roleId")));
        return id;
    }

    @Override
    public String objectToString(Object o) {
        UserRoleId id = (UserRoleId) o;
        StringBuilder sb = new StringBuilder();
        sb.append(id.getRoleId())
                .append(" ")
                .append(id.getUserId());

        return sb.toString();

    }

    @Override
    public void set(String string, Object o, Document dcmnt, LuceneOptions lo) {
        UserRoleId id = (UserRoleId) o;
        Store store = lo.getStore();
        Index index = lo.getIndex();
        TermVector termVector = lo.getTermVector();
        Float boost = lo.getBoost();
        Field field = new Field(string + ".userId", String.valueOf(id.getUserId()), store, index, termVector);
        field.setBoost(boost);
        dcmnt.add(field);

        field = new Field(string + ".roleId", String.valueOf(id.getRoleId()),
                store, index, termVector);
        field.setBoost(boost);
        dcmnt.add(field);

        field = new Field(string, objectToString(id), //store unique representation in named field
                store, index, termVector);
        field.setBoost(boost);
        dcmnt.add(field);
    }

}
