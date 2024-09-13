package com.example.csvccdshustbe.repository.houseDeclare.impl;

import com.example.csvccdshustbe.dto.declare.HouseDeclareDetailsDto;
import com.example.csvccdshustbe.entity.HouseDeclare;
import com.example.csvccdshustbe.repository.houseDeclare.HouseDeclareRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Transactional
    @Override
    public void deleteHouseDeclareById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from house_declare houseDeclare " +
                "where houseDeclare.id_house_declare = :idHouseDeclare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idHouseDeclare", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<HouseDeclare> findHouseDeclareById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select houseDeclare.id_house_declare, houseDeclare.id_asset, houseDeclare.workplace, " +
                "       houseDeclare.hdsn_no_bussiness, houseDeclare.hdsn_bussiness, houseDeclare.hdsn_rent, " +
                "       houseDeclare.hdsn_bonds, houseDeclare.blank_place, houseDeclare.encroached_place, " +
                "       houseDeclare.synthetic_use, houseDeclare.other_use, houseDeclare.acreage, " +
                "       houseDeclare.time_created, houseDeclare.time_modified, " +
                "       houseDeclare.id_type_declare_asset, houseDeclare.live_place " +
                "from house_declare houseDeclare " +
                "where houseDeclare.id_house_declare = :idHouseDeclare ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idHouseDeclare", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                HouseDeclare houseDeclare = new HouseDeclare();
                houseDeclare.setIdHouseDeclare(ValueUtil.getIntegerByObject(obj[0]));
                houseDeclare.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                houseDeclare.setWorkplace(ValueUtil.getDoubleByObject(obj[2]));
                houseDeclare.setHdsnNoBussiness(ValueUtil.getDoubleByObject(obj[3]));
                houseDeclare.setHdsnBussiness(ValueUtil.getDoubleByObject(obj[4]));
                houseDeclare.setHdsnRent(ValueUtil.getDoubleByObject(obj[5]));
                houseDeclare.setHdsnBonds(ValueUtil.getDoubleByObject(obj[6]));
                houseDeclare.setBlankPlace(ValueUtil.getDoubleByObject(obj[7]));
                houseDeclare.setEncroachedPlace(ValueUtil.getDoubleByObject(obj[8]));
                houseDeclare.setSyntheticUse(ValueUtil.getDoubleByObject(obj[9]));
                houseDeclare.setOtherUse(ValueUtil.getDoubleByObject(obj[10]));
                houseDeclare.setAcreage(ValueUtil.getDoubleByObject(obj[11]));
                houseDeclare.setTimeCreated(ValueUtil.getStringByObject(obj[12]));
                houseDeclare.setTimeModified(ValueUtil.getStringByObject(obj[13]));
                houseDeclare.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(obj[14]));
                houseDeclare.setLivePlace(ValueUtil.getDoubleByObject(obj[15]));
                return Optional.of(houseDeclare);
            }
        }
        return Optional.empty();
    }
}
