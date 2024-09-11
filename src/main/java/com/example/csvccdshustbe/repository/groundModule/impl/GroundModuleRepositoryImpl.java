package com.example.csvccdshustbe.repository.groundModule.impl;

import com.example.csvccdshustbe.entity.GroundModule;
import com.example.csvccdshustbe.repository.groundModule.GroundModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class GroundModuleRepositoryImpl implements GroundModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<GroundModule> findGroundModuleByIdGroundModule(Integer idGroundModule) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select groundModule.id_ground_module, groundModule.asset_id, groundModule.province_code, " +
                "       groundModule.district_code, groundModule.ward_code, groundModule.address_detail " +
                "from ground_module groundModule " +
                "where groundModule.id_ground_module = :idGroundModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundModule", idGroundModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                GroundModule module = new GroundModule();
                module.setIdGroundModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setProvinceCode(ValueUtil.getStringByObject(obj[2]));
                module.setDistrictCode(ValueUtil.getStringByObject(obj[3]));
                module.setWardCode(ValueUtil.getStringByObject(obj[4]));
                module.setAddressDetail(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }
}
