package com.example.csvccdshustbe.repository.houseDeclare.impl;

import com.example.csvccdshustbe.dto.declare.HouseDeclareDetailsDto;
import com.example.csvccdshustbe.repository.houseDeclare.HouseDeclareRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class HouseDeclareRepositoryImpl implements HouseDeclareRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<HouseDeclareDetailsDto> findHouseDeclareDetailsById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select house.id_house_declare, house.id_asset, house.workplace, " +
                "       house.hdsn_no_bussiness, house.hdsn_bussiness, house.hdsn_rent, " +
                "       house.hdsn_bonds, house.blank_place, house.encroached_place, " +
                "       house.synthetic_use, house.other_use, house.acreage, " +
                "       house.time_created, house.time_modified, house.id_type_declare_asset, " +
                "       house.live_place, ty.name nameTypeDeclareAsset " +
                "from house_declare house " +
                "    left join type_declare_asset ty on house.id_type_declare_asset = ty.id_type_declare_asset " +
                "where house.id_house_declare = :idHouse  ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idHouse", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                HouseDeclareDetailsDto detailsDto = new HouseDeclareDetailsDto();
                detailsDto.setIdHouseDeclare(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setWorkplace(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setHdsnNoBussiness(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setHdsnBussiness(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setHdsnRent(ValueUtil.getDoubleByObject(obj[5]));
                detailsDto.setHdsnBonds(ValueUtil.getDoubleByObject(obj[6]));
                detailsDto.setBlankPlace(ValueUtil.getDoubleByObject(obj[7]));
                detailsDto.setEncroachedPlace(ValueUtil.getDoubleByObject(obj[8]));
                detailsDto.setSyntheticUse(ValueUtil.getDoubleByObject(obj[9]));
                detailsDto.setOtherUse(ValueUtil.getDoubleByObject(obj[10]));
                detailsDto.setAcreage(ValueUtil.getDoubleByObject(obj[11]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                detailsDto.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[14]));
                detailsDto.setLivePlace(ValueUtil.getDoubleByObject(obj[15]));
                detailsDto.setNameTypeDeclareAsset(ValueUtil.getStringByObject(obj[16]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }
}
