package com.example.csvccdshustbe.repository.assetInstance.impl;

import com.example.csvccdshustbe.entity.AssetInstance;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.assetInstance.AssetInstanceRepositoryCustom;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
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

public class AssetInstanceRepositoryImpl implements AssetInstanceRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<AssetInstance> findAllAssetInstance(FindAllAssetInstanceRequest request,Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_asset_instance, id_user, value,   " +
                "       id_department_original, time_created, time_modified,  " +
                "       error " +
                "from asset_instance assetStance  " +
                "where assetStance.id_user = :idUser  " +
                "and assetStance.id_department_original = :idDepartmentOriginal ");
        setConditionFindAllAssetInstance(request,sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetInstance(query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<AssetInstance> assetInstance = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetInstance instance = new AssetInstance();
                instance.setIdAssetInstance(ValueUtil.getIntegerByObject(obj[0]));
                instance.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                instance.setValue(ValueUtil.getStringByObject(obj[2]));
                instance.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[3]));
                instance.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                instance.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                instance.setError(ValueUtil.getStringByObject(obj[6]));
                assetInstance.add(instance);
            }
        }
        return new PageImpl<>(assetInstance, pageable, countFindAllAssetInstance(request));
    }

    @Override
    public List<AssetInstance> findAllAssetInstanceByIds(List<Integer> idsAssetInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_asset_instance, id_user,   " +
                "       value, id_department_original,  " +
                "       time_created, time_modified  " +
                "from asset_instance   " +
                "where id_asset_instance in (:idsAssetInstance)  " +
                "and id_user = :idUser  " +
                "and id_department_original = :idDepartmentOriginal  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idsAssetInstance", idsAssetInstance);
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idDepartmentOriginal", csvcUser.getIdDepartmentCurrent());
        List<Object[]> result = query.getResultList();
        List<AssetInstance> assetInstance = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetInstance instance = new AssetInstance();
                instance.setIdAssetInstance(ValueUtil.getIntegerByObject(obj[0]));
                instance.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                instance.setValue(ValueUtil.getStringByObject(obj[2]));
                instance.setIdDepartmentOriginal(ValueUtil.getIntegerByObject(obj[3]));
                instance.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                instance.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                assetInstance.add(instance);
            }
        }
        return assetInstance;
    }

    @Override
    public long totalError() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from asset_instance assetStance " +
                "where assetStance.id_user = :idUser " +
                "and assetStance.id_department_original = :idDepartmentOriginal " +
                "and assetStance.error is not null ");
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllAssetInstance(query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
    private void setConditionFindAllAssetInstance(FindAllAssetInstanceRequest request, StringBuilder sb) {
        if (ObjectUtils.isNotEmpty(request.getIsError()) && request.getIsError().equals(Constants.IS_ERROR_INSTANCE)) {
            sb.append(" and assetStance.error is not null ");
        }
        else if (ObjectUtils.isNotEmpty(request.getIsError()) && request.getIsError().equals(Constants.IS_NOT_ERROR_INSTANCE)) {
            sb.append(" and assetStance.error is null ");
        }
    }
    private void setParameterFindAllAssetInstance(Query query) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        query.setParameter("idUser", csvcUser.getIdUser());
        query.setParameter("idDepartmentOriginal", csvcUser.getIdDepartmentCurrent());
    }

    private long countFindAllAssetInstance(FindAllAssetInstanceRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0)  " +
                "from asset_instance assetStance  " +
                "where assetStance.id_user = :idUser  " +
                "and assetStance.id_department_original = :idDepartmentOriginal ");
        Query query = entityManager.createNativeQuery(sb.toString());
        setConditionFindAllAssetInstance(request,sb);
        setParameterFindAllAssetInstance(query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
}
