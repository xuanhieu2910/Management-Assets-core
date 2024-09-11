package com.example.csvccdshustbe.repository.otherAssetModule.impl;

import com.example.csvccdshustbe.entity.OtherAssetModule;
import com.example.csvccdshustbe.repository.otherAssetModule.OtherAssetModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class OtherAssetModuleRepositoryImpl implements OtherAssetModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<OtherAssetModule> findOtherAssetModuleByIdOtherAssetModule(Integer idOtherAssetModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select otherAssetModules.id_other_asset_module, otherAssetModules.id_asset, otherAssetModules.label, " +
                "       otherAssetModules.model, otherAssetModules.serial, otherAssetModules.publish_date, " +
                "       otherAssetModules.id_country_producer, otherAssetModules.id_user, otherAssetModules.id_type_use " +
                "from other_asset_module otherAssetModules " +
                "where otherAssetModules.id_other_asset_module = :idOtherAssetModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOtherAssetModule", idOtherAssetModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                OtherAssetModule assetModule = new OtherAssetModule();
                assetModule.setIdOtherAssetModule(ValueUtil.getIntegerByObject(obj[0]));
                assetModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetModule.setLable(ValueUtil.getStringByObject(obj[2]));
                assetModule.setModel(ValueUtil.getStringByObject(obj[3]));
                assetModule.setSerial(ValueUtil.getStringByObject(obj[4]));
                assetModule.setPublishDate(ValueUtil.getStringByObject(obj[5]));
                assetModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[6]));
                assetModule.setIdUser(ValueUtil.getIntegerByObject(obj[7]));
                assetModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[8]));
            }
        }
        return Optional.empty();
    }
}
