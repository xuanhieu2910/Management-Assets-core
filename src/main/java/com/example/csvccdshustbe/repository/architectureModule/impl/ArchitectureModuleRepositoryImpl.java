package com.example.csvccdshustbe.repository.architectureModule.impl;

import com.example.csvccdshustbe.entity.ArchitectureModule;
import com.example.csvccdshustbe.repository.architectureModule.ArchitectureModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ArchitectureModuleRepositoryImpl implements ArchitectureModuleRepositoryCustom {
    
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<ArchitectureModule> findArchitectureModuleByIdArchitectureModule(Integer idArchitectureModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select architectureModule.id_architecture_module, architectureModule.id_asset, " +
                "       architectureModule.id_instance, architectureModule.length, architectureModule.acreage, " +
                "       architectureModule.volume, architectureModule.publish_date, architectureModule.id_country_producer " +
                "from architecture_module architectureModule " +
                "where architectureModule.id_architecture_module = :idArchitectureModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idArchitectureModule", idArchitectureModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
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
