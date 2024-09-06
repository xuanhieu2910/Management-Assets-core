package com.example.csvccdshustbe.repository.typeBuyAsset.impl;

import com.example.csvccdshustbe.entity.TypeBuyAsset;
import com.example.csvccdshustbe.repository.typeBuyAsset.TypeBuyAssetRepositoryCustom;
import com.example.csvccdshustbe.request.typeBuyAsset.FindAllTypeBuyAssetPickedRequest;
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

public class TypeBuyAssetRepositoryImpl implements TypeBuyAssetRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<TypeBuyAsset> findAllBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.id_type_buy_asset, t.title, t.status, " +
                "       t.time_created, t.time_modified " +
                "from type_buy_asset t " +
                "where 1 = 1 " +
                "and t.status = :status ");
        setConditionFindAllBuyAssetPicked(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllBuyAssetPicked(request, query);
        PageUtils.buildQuery(pageable, query);
        List<TypeBuyAsset> responses = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                TypeBuyAsset response = new TypeBuyAsset();
                response.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(obj[0]));
                response.setTitle(ValueUtil.getStringByObject(obj[1]));
                response.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                response.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                response.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                responses.add(response);
            }
        }
        return new PageImpl<>(responses, pageable, countFindAllBuyAssetPicked(request));
    }

    private void setParameterFindAllBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request, Query query) {
        query.setParameter("status", Constants.TYPE_BUY_ASSET_ACTIVE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append(" and (t.title REGEXP :keyword ) ");
        }
    }

    private long countFindAllBuyAssetPicked(FindAllTypeBuyAssetPickedRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from type_buy_asset t " +
                "where 1 = 1 " +
                "and t.status = :status ");
        setConditionFindAllBuyAssetPicked(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllBuyAssetPicked(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
}
