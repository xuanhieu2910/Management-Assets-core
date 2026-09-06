package com.example.csvccdshustbe.repository.typeProcess.impl;

import com.example.csvccdshustbe.entity.TypeProcess;
import com.example.csvccdshustbe.repository.typeProcess.TypeProcessRepositoryCustom;
import com.example.csvccdshustbe.request.typeProcess.FindAllTypeProcessRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TypeProcessRepositoryImpl implements TypeProcessRepositoryCustom {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<TypeProcess> findTypeProcessByCodeAndStatus(String codeProcess, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_type_process, name, code, " +
                "          description, time_created, " +
                "          time_modified, status " +
                "from type_process " +
                "where code = :code " +
                "and status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("code", codeProcess);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TypeProcess process = new TypeProcess();
                process.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[0]));
                process.setName(ValueUtil.getStringByObject(obj[1]));
                process.setCode(ValueUtil.getStringByObject(obj[2]));
                process.setDescription(ValueUtil.getStringByObject(obj[3]));
                process.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                process.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                process.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(process);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<TypeProcess> findTypeProcessByIdAndStatus(Integer idTypeProcess, Integer status) {
        StringBuilder sb = new StringBuilder();
        sb.append("select id_type_process, name, code,        " +
                "          description, time_created,   " +
                "          time_modified, status   " +
                "from type_process         " +
                "where id_type_process = :idTypeProcess    " +
                "and status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTypeProcess", idTypeProcess);
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TypeProcess process = new TypeProcess();
                process.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[0]));
                process.setName(ValueUtil.getStringByObject(obj[1]));
                process.setCode(ValueUtil.getStringByObject(obj[2]));
                process.setDescription(ValueUtil.getStringByObject(obj[3]));
                process.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                process.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                process.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(process);
            }
        }
        return Optional.empty();
    }

    @Override
    public Page<TypeProcess> findAllTypeProcessActive(FindAllTypeProcessRequest typeProcessRequest, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_type_process, name, " +
                "    code, description, " +
                "    time_created, time_modified " +
                "from type_process " +
                "where 1 = 1 " +
                "and status = :status ");
        setConditionFindAllTypeProcess(typeProcessRequest, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeProcess(typeProcessRequest,query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<TypeProcess> typeProcesses = new ArrayList<>();
        if (CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                TypeProcess typeProcess = new TypeProcess();
                typeProcess.setIdTypeProcess(ValueUtil.getIntegerByObject(obj[0]));
                typeProcess.setName(ValueUtil.getStringByObject(obj[1]));
                typeProcess.setCode(ValueUtil.getStringByObject(obj[2]));
                typeProcess.setDescription(ValueUtil.getStringByObject(obj[3]));
                typeProcess.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                typeProcess.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                typeProcesses.add(typeProcess);
            }
        }
        return new PageImpl<>(typeProcesses, pageable, countFindAllTypeProcess(typeProcessRequest));
    }

    private long countFindAllTypeProcess(FindAllTypeProcessRequest typeProcessRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from type_process  " +
                "where 1 = 1 " +
                "and status = :status ");
        setConditionFindAllTypeProcess(typeProcessRequest, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeProcess(typeProcessRequest, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllTypeProcess(FindAllTypeProcessRequest typeProcessRequest, Query query) {
        query.setParameter("status", Constants.STATUS_TYPE_PROCESS_ACTIVE);
    }

    private void setConditionFindAllTypeProcess(FindAllTypeProcessRequest typeProcessRequest, StringBuilder sb) {
    }
}
