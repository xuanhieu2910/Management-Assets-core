package com.example.csvccdshustbe.repository.methodBuyAsset.impl;

import com.example.csvccdshustbe.entity.MethodBuyAsset;
import com.example.csvccdshustbe.repository.methodBuyAsset.MethodBuyAssetRepositoryCustom;
import com.example.csvccdshustbe.request.methodBuyAsset.FindAllMethodBuyAssetPickedRequest;
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

public class MethodBuyAssetRepositoryImpl implements MethodBuyAssetRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<MethodBuyAsset> findAllActiveMethodBuyAsset(FindAllMethodBuyAssetPickedRequest request, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_method_buy_asset, title,    " +
                "       status, time_created, time_modified   " +
                "from method_buy_asset   " +
                "where 1 = 1   " +
                "and status = :status ");
        setConditionFindAllActiveMethodBuyAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllActiveMethodBuyAsset(request, query);
        PageUtils.buildQuery(pageable, query);
        List<Object[]> result = query.getResultList();
        List<MethodBuyAsset> methodBuyAssets = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                MethodBuyAsset methodBuyAsset = new MethodBuyAsset();
                methodBuyAsset.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(obj[0]));
                methodBuyAsset.setTitle(ValueUtil.getStringByObject(obj[1]));
                methodBuyAsset.setStatus(ValueUtil.getIntegerByObject(obj[2]));
                methodBuyAsset.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                methodBuyAsset.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                methodBuyAssets.add(methodBuyAsset);
            }
        }
        return new PageImpl<>(methodBuyAssets, pageable, countFindAllActiveMethodBuyAsset(request));
    }

    private void setParameterFindAllActiveMethodBuyAsset(FindAllMethodBuyAssetPickedRequest request, Query query) {
        query.setParameter("status", Constants.METHOD_BUY_ASSET_ACTIVE);
        if (StringUtils.isNotBlank(request.getKeyword())){
            query.setParameter("keyword", request.getKeyword());
        }
    }

    private void setConditionFindAllActiveMethodBuyAsset(FindAllMethodBuyAssetPickedRequest request, StringBuilder sb) {
        if (StringUtils.isNotBlank(request.getKeyword())){
            sb.append("   and (m.title REGEXP :keyword ) ");
        }
    }

    private long countFindAllActiveMethodBuyAsset(FindAllMethodBuyAssetPickedRequest request){
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(0) " +
                "from method_buy_asset m " +
                "where 1 = 1 " +
                "and m.status = :status ");
        setConditionFindAllActiveMethodBuyAsset(request, sb);
        Query query = entityManager.createNativeQuery(sb.toString());
        setParameterFindAllActiveMethodBuyAsset(request, query);
        return ValueUtil.getLongByObject(query.getSingleResult());
    }
}
