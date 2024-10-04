package com.example.csvccdshustbe.repository.typeUse.impl;


import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepositoryCustom;
import com.example.csvccdshustbe.request.typeUse.FindAllTypeUseRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TypeUseRepositoryImpl implements TypeUseRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<TypeUse> findAllTypeUseActiveResponse(FindAllTypeUseRequest request, Pageable pageable){
        StringBuilder sb = new StringBuilder();
        sb.append(" Select type_use.id_type_use, type_use.name, type_use.status, " +
                "type_use.time_created, type_use.time_modified from type_use " +
                "where 1=1 and type_use.status = :status ");
        setConditionFindAllTypeUseActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeUseActive(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<TypeUse> typeUses = new ArrayList<>();
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
        return new PageImpl<>(typeUses, pageable, countFindAllTypeUseActive(request));
    }

    private long countFindAllTypeUseActive(FindAllTypeUseRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from type_use  " +
                "where 1 = 1 " +
                "and type_use.status = :status ");
        setConditionFindAllTypeUseActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeUseActive(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllTypeUseActive(FindAllTypeUseRequest request, Query query) {
        query.setParameter("status", Constants.DOCUMENT_ATTACK_ACTIVE_STATUS);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllTypeUseActive(FindAllTypeUseRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (type_use.name REGEXP :keyword ) ");
        }
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

    @Override
    public List<TypeUse> findAllTypeUseByIds(List<Integer> typeUseIds) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ty.id_type_use, ty.name, " +
                "       ty.time_created, ty.time_modified, ty.status " +
                "from type_use ty " +
                "where ty.id_type_use in :idTypeUse ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTypeUse", typeUseIds);
        List<Object[]> result = query.getResultList();
        List<TypeUse> typeUseList= new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                TypeUse typeUse = new TypeUse();
                typeUse.setIdTypeUse(ValueUtil.getIntegerByObject(obj[0]));
                typeUse.setName(ValueUtil.getStringByObject(obj[1]));
                typeUse.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                typeUse.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                typeUse.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                typeUseList.add(typeUse);
            }
        }
        return typeUseList;
    }
}
