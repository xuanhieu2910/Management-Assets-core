package com.example.csvccdshustbe.response.assetModules.impl;

import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.entity.AssetModules;
import com.example.csvccdshustbe.response.assetModules.AssetModulesRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AssetModulesRepositoryImpl implements AssetModulesRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<AssetModules> findAllAssetModulesByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetModules.id_asset_module, assetModules.id_asset, " +
                "       assetModules.id_module, assetModules.id_instance, " +
                "       assetModules.time_created, assetModules.time_modified " +
                "from asset_modules assetModules " +
                "where assetModules.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<AssetModules> assetModules = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                AssetModules asset = new AssetModules();
                asset.setIdAssetModule(ValueUtil.getIntegerByObject(obj[0]));
                asset.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                asset.setIdModule(ValueUtil.getIntegerByObject(obj[2]));
                asset.setIdInstance(ValueUtil.getIntegerByObject(obj[3]));
                asset.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                asset.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                assetModules.add(asset);
            }
        }
        return assetModules;
    }

    @Override
    public List<BluePrintAssetModulesDto> findBluePrintAssetModulesDtoByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select modules.code typeModules, modules.id_module, " +
                "       modules.name nameModules, assetModules.id_instance " +
                "from asset asset " +
                "    inner join asset_modules assetModules on asset.id_asset = assetModules.id_asset " +
                "    inner join modules modules on assetModules.id_module = modules.id_module " +
                "where asset.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<BluePrintAssetModulesDto> assetModulesDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                BluePrintAssetModulesDto dto = new BluePrintAssetModulesDto();
                dto.setTypeModules(ValueUtil.getStringByObject(obj[0]));
                dto.setIdModules(ValueUtil.getIntegerByObject(obj[1]));
                dto.setNameModules(ValueUtil.getStringByObject(obj[2]));
                dto.setIdInstance(ValueUtil.getIntegerByObject(obj[3]));
                assetModulesDtos.add(dto);
            }
        }
        return assetModulesDtos;
    }


    @Modifying
    @Transactional
    @Override
    public void deleteAssetModulesByIdInstanceAndIdModule(Integer idInstance, Integer idModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from asset_modules assetModules " +
                "where assetModules.id_instance = :idInstance and assetModules.id_module = :idModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idInstance", idInstance);
        query.setParameter("idModule", idModule);
        query.executeUpdate();
    }
}
