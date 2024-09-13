package com.example.csvccdshustbe.repository.architectureModule.impl;

import com.example.csvccdshustbe.dto.modules.architectureModules.ArchitectureModulesDetailsDto;
import com.example.csvccdshustbe.entity.ArchitectureModule;
import com.example.csvccdshustbe.repository.architectureModule.ArchitectureModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ArchitectureModuleRepositoryImpl implements ArchitectureModuleRepositoryCustom {
    
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<ArchitectureModulesDetailsDto> findArchitectureModuleDetailsDtoByIdArchitectureModule(Integer idArchitectureModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select architectureModule.id_architecture_module, architectureModule.id_asset, " +
                "    architectureModule.id_instance, architectureModule.length, architectureModule.acreage, " +
                "    architectureModule.volume, architectureModule.publish_date, architectureModule.id_country_producer, " +
                "    a.name nameInstance, co.name nameCountryProducer " +
                "from architecture_module architectureModule " +
                "    left join csvc.asset a on architectureModule.id_asset = a.id_asset " +
                "    left join country_producer co on architectureModule.id_country_producer = co.id_country_producer " +
                "where architectureModule.id_architecture_module = :idArchitectureModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idArchitectureModule", idArchitectureModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ArchitectureModulesDetailsDto module = new ArchitectureModulesDetailsDto();
                module.setIdArchitectureModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setIdInstance(ValueUtil.getIntegerByObject(obj[2]));
                module.setLength(ValueUtil.getDoubleByObject(obj[3]));
                module.setAcreage(ValueUtil.getDoubleByObject(obj[4]));
                module.setVolume(ValueUtil.getDoubleByObject(obj[5]));
                module.setPublishDate(ValueUtil.getStringByObject(obj[6]));
                module.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[7]));
                module.setNameInstance(ValueUtil.getStringByObject(obj[8]));
                module.setNameCountryProducer(ValueUtil.getStringByObject(obj[9]));
                module.setNameInstance(ValueUtil.getStringByObject(obj[10]));
                module.setNameCountryProducer(ValueUtil.getStringByObject(obj[11]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteArchitectureModuleByIdArchitecture(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from architecture_module ar " +
                "where ar.id_architecture_module = :idAr");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAr", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ArchitectureModule> findArchitectureModuleByIdArchitectureModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ar.id_architecture_module, ar.id_asset, ar.id_instance, " +
                "       ar.length, ar.acreage, ar.volume, ar.publish_date, " +
                "       ar.id_country_producer " +
                "from architecture_module ar  " +
                "where ar.id_architecture_module = :idArchitectureModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idArchitectureModule", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                ArchitectureModule module = new ArchitectureModule();
                module.setIdArchitectureModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setIdInstance(ValueUtil.getIntegerByObject(obj[2]));
                module.setLength(ValueUtil.getDoubleByObject(obj[3]));
                module.setAcreage(ValueUtil.getDoubleByObject(obj[4]));
                module.setVolume(ValueUtil.getDoubleByObject(obj[5]));
                module.setPublishDate(ValueUtil.getStringByObject(obj[6]));
                module.setIdCountryProducer(ValueUtil.getIntegerByObject(obj[7]));
                return Optional.of(module);
            }
        }

        return Optional.empty();
    }
}
