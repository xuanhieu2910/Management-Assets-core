package com.example.csvccdshustbe.repository.levelTypeAsset.impl;

import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.entity.Suppliers;
import com.example.csvccdshustbe.repository.levelTypeAsset.LevelTypeAssetRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    @Override
    public Optional<LevelTypeAsset> findLevelTypeAssetByName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select lta.id_level_type_asset, lta.name, " +
                "lta.level, lta.description, " +
                "lta.time_created, lta.time_modified, lta.status " +
                "from level_type_asset lta " +
                "where lta.name = :name ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("name", name);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                LevelTypeAsset levelTypeAsset = new LevelTypeAsset();
                levelTypeAsset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[0]));
                levelTypeAsset.setName(ValueUtil.getStringByObject(obj[1]));
                levelTypeAsset.setLevel(ValueUtil.getIntegerByObject(obj[2]));
                levelTypeAsset.setDescription(ValueUtil.getStringByObject(obj[3]));
                levelTypeAsset.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                levelTypeAsset.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                levelTypeAsset.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(levelTypeAsset);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<LevelTypeAsset> findLevelTypeAssetById(Integer idLevelTypeAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select lta.id_level_type_asset, lta.name, " +
                "lta.level, lta.description, " +
                "lta.time_created, lta.time_modified, lta.status " +
                "from level_type_asset lta " +
                "where lta.id_level_type_asset = :idLevelTypeAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idLevelTypeAsset", idLevelTypeAsset);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                LevelTypeAsset levelTypeAsset = new LevelTypeAsset();
                levelTypeAsset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(obj[0]));
                levelTypeAsset.setName(ValueUtil.getStringByObject(obj[1]));
                levelTypeAsset.setLevel(ValueUtil.getIntegerByObject(obj[2]));
                levelTypeAsset.setDescription(ValueUtil.getStringByObject(obj[3]));
                levelTypeAsset.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                levelTypeAsset.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                levelTypeAsset.setStatus(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(levelTypeAsset);
            }
        }
        return Optional.empty();
    }
}
