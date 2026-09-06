package com.example.csvccdshustbe.repository.typeDeclareAsset.impl;

import com.example.csvccdshustbe.entity.TypeDeclareAsset;
import com.example.csvccdshustbe.repository.typeDeclareAsset.TypeDeclareAssetRepositoryCustom;
import com.example.csvccdshustbe.request.typeDeclareAsset.FindAllTypeDeclareAssetActiveRequest;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TypeDeclareAssetRepositoryImpl implements TypeDeclareAssetRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;



    @Override
    public Page<TypeDeclareAsset> findAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select typeDeclareAsset.id_type_declare_asset, typeDeclareAsset.name,  " +
                "       typeDeclareAsset.status, typeDeclareAsset.time_created,  " +
                "       typeDeclareAsset.time_modified,  " +
                "       typeDeclareAsset.id_asset_category " +
                "from type_declare_asset typeDeclareAsset " +
                "    inner join asset_categories assetCategory " +
                "        on typeDeclareAsset.id_asset_category = assetCategory.id_asset_category " +
                "where 1 = 1  " +
                "and typeDeclareAsset.status = :status " +
                "and assetCategory.id_asset_category = :idAssetCategory ");
        setConditionFindAllTypeDeclareAssetActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeDeclareAssetActive(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<TypeDeclareAsset> typeDeclareAssets = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                TypeDeclareAsset typeDeclareAsset = new TypeDeclareAsset();
                typeDeclareAsset.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[0]));
                typeDeclareAsset.setName(ValueUtil.getStringByObject(obj[1]));
                typeDeclareAsset.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                typeDeclareAsset.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                typeDeclareAsset.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                typeDeclareAsset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[5]));
                typeDeclareAssets.add(typeDeclareAsset);
            }
        }
        return new PageImpl<>(typeDeclareAssets, pageable, countFindAllTypeDeclareAssetActive(request));
    }



    private long countFindAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from type_declare_asset typeDeclareAsset " +
                "    inner join asset_categories assetCategory " +
                "        on typeDeclareAsset.id_asset_category = assetCategory.id_asset_category " +
                "where 1 = 1 " +
                "and typeDeclareAsset.status = :status " +
                "and assetCategory.id_asset_category = :idAssetCategory ");
        setConditionFindAllTypeDeclareAssetActive(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllTypeDeclareAssetActive(request,query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }

    private void setParameterFindAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request, Query query) {
        query.setParameter("status", Constants.TYPE_DECLARE_ASSET_ACTIVE);
        query.setParameter("idAssetCategory", request.getIdAssetCategory());
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllTypeDeclareAssetActive(FindAllTypeDeclareAssetActiveRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            sb.append(" and (typeDeclareAsset.name REGEXP :keyword ) ");
        }
    }


    @Override
    public Optional<TypeDeclareAsset> findTypeDeclareAssetByIdAssetCategory(Integer idAssetCategory) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select typeDeclareAsset.id_type_declare_asset, typeDeclareAsset.name,  " +
                "       typeDeclareAsset.status, typeDeclareAsset.time_created,  " +
                "       typeDeclareAsset.time_modified,  " +
                "       typeDeclareAsset.id_asset_category " +
                "from type_declare_asset typeDeclareAsset " +
                "where typeDeclareAsset.id_asset_category = :idAssetCategory ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetCategory", idAssetCategory);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                TypeDeclareAsset typeDeclareAsset = new TypeDeclareAsset();
                typeDeclareAsset.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[0]));
                typeDeclareAsset.setName(ValueUtil.getStringByObject(obj[1]));
                typeDeclareAsset.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                typeDeclareAsset.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                typeDeclareAsset.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                typeDeclareAsset.setIdAssetCategory(ValueUtil.getIntegerByObject(obj[5]));
                return Optional.of(typeDeclareAsset);
            }
        }
        return Optional.empty();
    }
}
