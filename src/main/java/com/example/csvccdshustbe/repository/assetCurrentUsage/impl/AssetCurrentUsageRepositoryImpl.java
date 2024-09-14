package com.example.csvccdshustbe.repository.assetCurrentUsage.impl;

import com.example.csvccdshustbe.entity.AssetCurrentUsage;
import com.example.csvccdshustbe.repository.assetCurrentUsage.AssetCurrentUsageRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AssetCurrentUsageRepositoryImpl implements AssetCurrentUsageRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Modifying
    @Transactional
    @Override
    public void deleteAssetCurrentUsageByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete " +
                "from asset_current_usage " +
                "where asset_current_usage.id_asset = :idAsset  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        query.executeUpdate();
    }

    @Override
    public List<AssetCurrentUsage> findByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select acu.id_asset_current_usage, acu.id_asset, " +
                "       acu.id_current_usage, acu.time_created " +
                "from asset_current_usage acu  " +
                "where acu.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<AssetCurrentUsage> usages = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                AssetCurrentUsage usage = new AssetCurrentUsage();
                usage.setIdAssetCurrentUsage(ValueUtil.getIntegerByObject(obj[0]));
                usage.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                usage.setIdCurrentUsage(ValueUtil.getIntegerByObject(obj[2]));
                usage.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                usages.add(usage);
            }
        }
        return usages;
    }
}
