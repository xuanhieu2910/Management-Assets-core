package com.example.csvccdshustbe.repository.groundModule.impl;

import com.example.csvccdshustbe.dto.modules.groundModules.GroundModulesDetailsDto;
import com.example.csvccdshustbe.entity.GroundModule;
import com.example.csvccdshustbe.repository.groundModule.GroundModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class GroundModuleRepositoryImpl implements GroundModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<GroundModulesDetailsDto> findGroundModuleDetailsDtoByIdGroundModule(Integer idGroundModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select groundModule.id_ground_module, groundModule.asset_id, groundModule.province_code,   " +
                "       groundModule.district_code, groundModule.ward_code, groundModule.address_detail, " +
                "       pr.name nameProvinces, dis.name nameDistricts, wa.name nameWards " +
                "from ground_module groundModule   " +
                "    left join  provinces pr on groundModule.province_code = pr.code " +
                "    left join  districts dis on groundModule.district_code = dis.code " +
                "    left join  wards wa on groundModule.ward_code = wa.code " +
                "where groundModule.id_ground_module = :idGroundModule  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundModule", idGroundModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                GroundModulesDetailsDto module = new GroundModulesDetailsDto();
                module.setIdGroundModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setProvinceCode(ValueUtil.getStringByObject(obj[2]));
                module.setDistrictCode(ValueUtil.getStringByObject(obj[3]));
                module.setWardCode(ValueUtil.getStringByObject(obj[4]));
                module.setAddressDetail(ValueUtil.getStringByObject(obj[5]));
                module.setNameProvince(ValueUtil.getStringByObject(obj[6]));
                module.setNameDistrict(ValueUtil.getStringByObject(obj[7]));
                module.setNameWard(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }


    @Modifying
    @Transactional
    @Override
    public void deleteGroundModuleByIdGroundModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ground_module groundModule " +
                "where groundModule.id_ground_module = :idGroundModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundModule", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<GroundModule> findGroundModuleByIdGroundModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ground.id_ground_module, ground.asset_id, ground.province_code, " +
                "       ground.district_code, ground.ward_code, ground.address_detail " +
                "from ground_module ground " +
                "where ground.id_ground_module = :idGroundModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idGroundModule", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
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
