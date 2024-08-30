package com.example.csvccdshustbe.repository.levelTypeAsset.impl;

import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.repository.levelTypeAsset.LevelTypeAssetRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class LevelTypeAssetRepositoryImpl implements LevelTypeAssetRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<LevelTypeAsset> findAllLevelTypeAssetByStatus(Integer status){
        StringBuilder sb = new StringBuilder();
        sb.append("select level_type_asset.id_level_type_asset,level_type_asset.name, " +
                "level_type_asset.level, level_type_asset.description," +
                "level_type_asset.status, level_type_asset.time_created, level_type_asset.time_modified " +
                "from level_type_asset " +
                "where 1=1 and level_type_asset.status = :status ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("status", status);
        List<Object[]> result = query.getResultList();
        List<LevelTypeAsset>levelTypeAssets= new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for(Object[] obj : result){
             LevelTypeAsset levelTypeAsset=new LevelTypeAsset();
             levelTypeAsset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[0]));
             levelTypeAsset.setName(ValueUtil.getStringByObject(obj[1]));
             levelTypeAsset.setLevel(ValueUtil.getIntegerByObject(obj[2]));
             levelTypeAsset.setDescription(ValueUtil.getStringByObject(obj[3]));
             levelTypeAsset.setStatus(ValueUtil.getIntegerByObject(obj[4]));
             levelTypeAsset.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
             levelTypeAsset.setTimeModified(ValueUtil.getStringByObject(obj[6]));
             levelTypeAssets.add(levelTypeAsset);
            }

        }
        return levelTypeAssets;
    }
}
