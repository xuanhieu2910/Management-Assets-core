package com.example.csvccdshustbe.repository.typeState.impl;

import com.example.csvccdshustbe.entity.TypeState;
import com.example.csvccdshustbe.repository.typeState.TypeStateRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypeStateRepositoryImpl implements TypeStateRepositoryCustom {
    
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<TypeState> findTypeStatesByListCodeAndStatus(List<String> codes, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_type_state, code, name,   " +
                "       status, time_created, time_modified  " +
                "from type_state  " +
                "where status = :status  " +
                "and code in (:code) ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        query.setParameter("code", codes);
        List<TypeState> responses = new ArrayList<>();
        List<Objects[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TypeState typeState = new TypeState();
                typeState.setIdTypeState(ValueUtil.getIntegerByObject(obj[0]));
                typeState.setCode(ValueUtil.getStringByObject(obj[1]));
                typeState.setName(ValueUtil.getStringByObject(obj[2]));
                typeState.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                typeState.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                typeState.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                responses.add(typeState);
            }
        }
        return responses;
    }

    @Override
    public List<TypeState> findAllTypeStateByStatus(Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ts.id_type_state, ts.code, ts.name, " +
                "       ts.status, ts.time_created, ts.time_modified " +
                "from type_state ts  " +
                "where ts.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<TypeState> typeStates = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TypeState typeState = new TypeState();
                typeState.setIdTypeState(ValueUtil.getIntegerByObject(obj[0]));
                typeState.setCode(ValueUtil.getStringByObject(obj[1]));
                typeState.setName(ValueUtil.getStringByObject(obj[2]));
                typeState.setStatus(ValueUtil.getIntegerByObject(obj[3]));
                typeState.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                typeState.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                typeStates.add(typeState);
            }
        }
        return typeStates;
    }
}
