package com.example.csvccdshustbe.repository.typeUse.impl;


import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TypeUseRepositoryImpl implements TypeUseRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<TypeUse> findAllTypeUseResponseByStatus(Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append(" Select type_use.id_type_use, type_use.name, type_use.status, " +
                "type_use.time_created, type_use.time_modified from type_use " +
                "where 1=1 and type_use.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<TypeUse> typeUses=new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                TypeUse typeUse= new TypeUse();
                typeUse.setIdTypeUse(ValueUtil.getIntegerByObject(obj[0]));
                typeUse.setName(ValueUtil.getStringByObject(obj[1]));
                typeUse.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                typeUse.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                typeUse.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                typeUses.add(typeUse);
            }
        }
        return typeUses;
    }

    @Override
    public Optional<TypeUse> findTypeUseByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ty.id_type_use, ty.name, " +
                "       ty.time_created, ty.time_modified, ty.status " +
                "from type_use ty " +
                "where ty.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                TypeUse typeUse = new TypeUse();
                typeUse.setIdTypeUse(ValueUtil.getIntegerByObject(obj[0]));
                typeUse.setName(ValueUtil.getStringByObject(obj[1]));
                typeUse.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                typeUse.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                typeUse.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                return Optional.of(typeUse);
            }
        }
        return Optional.empty();
    }
    @Override
    public Optional<TypeUse> findTypeUseById(Integer idTypeUse) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ty.id_type_use, ty.name, " +
                "       ty.time_created, ty.time_modified, ty.status " +
                "from type_use ty " +
                "where ty.id_type_use = :idTypeUse ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTypeUse", idTypeUse);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                TypeUse typeUse = new TypeUse();
                typeUse.setIdTypeUse(ValueUtil.getIntegerByObject(obj[0]));
                typeUse.setName(ValueUtil.getStringByObject(obj[1]));
                typeUse.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                typeUse.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                typeUse.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                return Optional.of(typeUse);
            }
        }
        return Optional.empty();
    }
}
