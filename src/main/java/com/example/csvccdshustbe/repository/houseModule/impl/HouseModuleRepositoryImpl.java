package com.example.csvccdshustbe.repository.houseModule.impl;

import com.example.csvccdshustbe.dto.modules.houseModules.HouseModuleDetailsDto;
import com.example.csvccdshustbe.entity.HouseModule;
import com.example.csvccdshustbe.repository.houseModule.HouseModuleRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class HouseModuleRepositoryImpl implements HouseModuleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<HouseModuleDetailsDto> findHouseModuleDetailsDtoByIdHouseModule(Integer idHouseModule) {
        StringBuilder sb = new StringBuilder();
        sb.append("select houseModule.id_house_module, houseModule.id_asset, houseModule.is_manage_ground,  " +
                "      houseModule.province_code, houseModule.district_code, houseModule.ward_code,  " +
                "      houseModule.address_detail, houseModule.floors_number, houseModule.acreage,  " +
                "      houseModule.publish_year, houseModule.id_instance, " +
                "      pro.name nameProvince, di.name nameDistricts, wa.name nameWards, " +
                "      asset.name nameInstance " +
                "from house_module houseModule " +
                "    left join provinces pro on houseModule.province_code = pro.code " +
                "    left join districts di on houseModule.district_code = di.code " +
                "    left join wards wa on houseModule.ward_code = wa.code " +
                "    left join asset asset on houseModule.id_instance = asset.id_asset " +
                "where houseModule.id_house_module = :idHouseModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idHouseModule", idHouseModule);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                HouseModuleDetailsDto module = new HouseModuleDetailsDto();
                module.setIdHouseModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setIsManageGround(ValueUtil.getIntegerByObject(obj[2]));
                module.setProvinceCode(ValueUtil.getStringByObject(obj[3]));
                module.setDistrictCode(ValueUtil.getStringByObject(obj[4]));
                module.setWardCode(ValueUtil.getStringByObject(obj[5]));
                module.setAddressDetail(ValueUtil.getStringByObject(obj[6]));
                module.setFloorsNumber(ValueUtil.getIntegerByObject(obj[7]));
                module.setAcreage(ValueUtil.getDoubleByObject(obj[8]));
                module.setPublishYear(ValueUtil.getStringByObject(obj[9]));
                module.setIdInstance(ValueUtil.getIntegerByObject(obj[10]));
                module.setNameProvince(ValueUtil.getStringByObject(obj[11]));
                module.setNameDistrict(ValueUtil.getStringByObject(obj[12]));
                module.setNameWard(ValueUtil.getStringByObject(obj[13]));
                module.setNameInstance(ValueUtil.getStringByObject(obj[14]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteHouseModuleByIdHouseModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from house_module  " +
                "where house_module.id_house_module = :idHouseModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idHouseModule", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<HouseModule> findHouseModuleByIdHouseModule(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select houseModule.id_house_module, houseModule.id_asset, houseModule.is_manage_ground, " +
                "       houseModule.province_code, houseModule.district_code, houseModule.ward_code, " +
                "       houseModule.address_detail, houseModule.floors_number, houseModule.acreage, " +
                "       houseModule.publish_year, houseModule.id_instance " +
                "from house_module houseModule " +
                "where houseModule.id_house_module = :idHouseModule ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idHouseModule", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                HouseModule module = new HouseModule();
                module.setIdHouseModule(ValueUtil.getIntegerByObject(obj[0]));
                module.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                module.setIsManageGround(ValueUtil.getIntegerByObject(obj[2]));
                module.setProvinceCode(ValueUtil.getStringByObject(obj[3]));
                module.setDistrictCode(ValueUtil.getStringByObject(obj[4]));
                module.setWardCode(ValueUtil.getStringByObject(obj[5]));
                module.setAddressDetail(ValueUtil.getStringByObject(obj[6]));
                module.setFloorsNumber(ValueUtil.getIntegerByObject(obj[7]));
                module.setAcreage(ValueUtil.getDoubleByObject(obj[8]));
                module.setPublishYear(ValueUtil.getStringByObject(obj[9]));
                module.setIdInstance(ValueUtil.getIntegerByObject(obj[10]));
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }
}
