package com.example.csvccdshustbe.repository.toolInstance.impl;

import com.example.csvccdshustbe.entity.AssetInstance;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.ToolInstance;
import com.example.csvccdshustbe.repository.toolInstance.ToolInstanceRepositoryCustom;
import com.example.csvccdshustbe.request.toolInstance.FindAllToolInstanceRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ToolInstanceRepositoryImpl implements ToolInstanceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<ToolInstance> findAllToolInstance(FindAllToolInstanceRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_tool_instance, id_user, value,   " +
                "       id_department_original, time_created, time_modified,  " +
                "       error " +
                "from tool_instance toolInStance  " +
                "where toolInStance.id_user = :idUser  " +
                "and toolInStance.id_department_original = :idDepartmentOriginal ");
        setConditionFindAllToolInstance(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolInstance(query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<ToolInstance> toolInstances = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                ToolInstance instance = new ToolInstance();
                instance.setIdToolInstance(ValueUtil.getIntegerByObject(obj[0]));
                instance.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                instance.setValue(ValueUtil.getStringByObject(obj[2]));
                instance.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[3]));
                instance.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                instance.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                instance.setError(ValueUtil.getStringByObject(obj[6]));
                toolInstances.add(instance);
            }
        }
        return new PageImpl<>(toolInstances, pageable, countFindAllToolInstance(request));
    }

    @Override
    public long totalErrorToolInstance() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from tool_instance toolInStance " +
                "where toolInStance.id_user = :idUser " +
                "and toolInStance.id_department_original = :idDepartmentOriginal " +
                "and toolInStance.error is not null ");
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolInstance(query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    @Override
    public List<ToolInstance> findAllToolInstanceByIds(List<Integer> idsToolInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_asset_instance, id_user,   " +
                "       value, id_department_original,  " +
                "       time_created, time_modified  " +
                "from tool_instance   " +
                "where id_tool_instance in (:idsToolInstance)  " +
                "and id_user = :idUser  " +
                "and id_department_original = :idDepartmentOriginal  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idsToolInstance", idsToolInstance);
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idDepartmentOriginal", csvcUser.getIdDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        List<ToolInstance> toolInstances = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                ToolInstance instance = new ToolInstance();
                instance.setIdToolInstance(ValueUtil.getIntegerByObject(obj[0]));
                instance.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                instance.setValue(ValueUtil.getStringByObject(obj[2]));
                instance.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[3]));
                instance.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                instance.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                toolInstances.add(instance);
            }
        }
        return toolInstances;
    }

    private long countFindAllToolInstance(FindAllToolInstanceRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  " +
                "from tool_instance toolInStance   " +
                "where toolInStance.id_user = :idUser  " +
                "and toolInStance.id_department_original = :idDepartmentOriginal ");
        setConditionFindAllToolInstance(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllToolInstance(query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllToolInstance(Query query) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idDepartmentOriginal", csvcUser.getIdDepartmentCurrent());
    }

    private void setConditionFindAllToolInstance(FindAllToolInstanceRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getIsError()) && request.getIsError().equals(Constants.IS_ERROR_INSTANCE)) {
            sb.append(" and toolInStance.error is not null ");
        }
        else if (ObjectUtils.isNotEmpty(request.getIsError()) && request.getIsError().equals(Constants.IS_NOT_ERROR_INSTANCE)) {
            sb.append(" and toolInStance.error is null ");
        }
    }
}
