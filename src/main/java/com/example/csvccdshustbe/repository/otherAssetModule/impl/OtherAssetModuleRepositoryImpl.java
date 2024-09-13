package com.example.csvccdshustbe.repository.otherAssetModule.impl;

import com.example.csvccdshustbe.dto.modules.otherAssetModules.OtherAssetModulesDetailsDto;
import com.example.csvccdshustbe.entity.OtherAssetModule;
import com.example.csvccdshustbe.repository.otherAssetModule.OtherAssetModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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
    public Optional<OtherAssetModulesDetailsDto> findOtherAssetModuleDetailsDtoByIdOtherAssetModule(Integer idOtherAssetModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select otherAssetModules.id_other_asset_module, otherAssetModules.id_asset, otherAssetModules.label,  " +
                "        otherAssetModules.model, otherAssetModules.serial, otherAssetModules.publish_date,  " +
                "        otherAssetModules.id_country_producer, otherAssetModules.id_user, otherAssetModules.id_type_use, " +
                "        co.name nameCountryProducer, us.user_name, us.full_name, ty.name nameTypeUse " +
                "from other_asset_module otherAssetModules  " +
                "    left join country_producer co on otherAssetModules.id_country_producer = co.id_country_producer " +
                "    left join csvc_user us on otherAssetModules.id_user = us.id_user " +
                "    left join type_use ty on otherAssetModules.id_type_use = ty.id_type_use " +
                "where otherAssetModules.id_other_asset_module = :idOtherAssetModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOtherAssetModule", idOtherAssetModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                OtherAssetModulesDetailsDto assetModule = new OtherAssetModulesDetailsDto();
                assetModule.setIdOtherAssetModule(ValueUtil.getIntegerByObject(obj[0]));
                assetModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetModule.setLabel(ValueUtil.getStringByObject(obj[2]));
                assetModule.setModel(ValueUtil.getStringByObject(obj[3]));
                assetModule.setSerial(ValueUtil.getStringByObject(obj[4]));
                assetModule.setPublishDate(ValueUtil.getStringByObject(obj[5]));
                assetModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[6]));
                assetModule.setIdUser(ValueUtil.getIntegerByObject(obj[7]));
                assetModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[8]));
                assetModule.setNameCountryProducer(ValueUtil.getStringByObject(obj[9]));
                assetModule.setUserName(ValueUtil.getStringByObject(obj[10]));
                assetModule.setFullName(ValueUtil.getStringByObject(obj[11]));
                assetModule.setNameTypeUse(ValueUtil.getStringByObject(obj[12]));
                return Optional.of(assetModule);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteOtherAssetModuleByIdOtherAsset(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from other_asset_module ot " +
                "where ot.id_other_asset_module = :idOtherAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOtherAsset", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<OtherAssetModule> findOtherAssetModuleByIdOtherAssetModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select oth.id_other_asset_module, oth.id_asset, oth.label, " +
                "       oth.model, oth.serial, oth.publish_date, oth.id_country_producer, " +
                "       oth.id_user, oth.id_type_use  " +
                "from other_asset_module oth  " +
                "where oth.id_other_asset_module = :idOtherAssetModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idOtherAssetModule", idInstance);
        List<Object[]> result = query.getResultList();
        if (CollectionUtils.isEmpty(result)) {
          for (Object[] obj : result){
              OtherAssetModule assetModule = new OtherAssetModule();
              assetModule.setIdOtherAssetModule(ValueUtil.getIntegerByObject(obj[0]));
              assetModule.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
              assetModule.setLabel(ValueUtil.getStringByObject(obj[2]));
              assetModule.setModel(ValueUtil.getStringByObject(obj[3]));
              assetModule.setSerial(ValueUtil.getStringByObject(obj[4]));
              assetModule.setPublishDate(ValueUtil.getStringByObject(obj[5]));
              assetModule.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[6]));
              assetModule.setIdUser(ValueUtil.getIntegerByObject(obj[7]));
              assetModule.setIdTypeUse(ValueUtil.getIntegerByObject(obj[8]));
              return Optional.of(assetModule);
          }
        }
        return Optional.empty();
    }
}
