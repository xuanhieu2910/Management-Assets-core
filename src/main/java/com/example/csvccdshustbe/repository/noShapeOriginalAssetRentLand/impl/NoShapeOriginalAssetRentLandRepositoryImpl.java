package com.example.csvccdshustbe.repository.noShapeOriginalAssetRentLand.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetRentLandDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetRentLand.NoShapeOriginalAssetRentLandRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class NoShapeOriginalAssetRentLandRepositoryImpl implements NoShapeOriginalAssetRentLandRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Optional<NoShapeOriginalAssetRentLandDetailsDto> findNoShapeOriginalAssetRentLandDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_rent_land, shape.id_asset,  " +
                "       shape.value_rent, shape.value_work,  " +
                "       shape.time_created, shape.time_modified  " +
                "from ns_original_asset_rent_land shape  " +
                "where shape.id_ns_original_asset_rent_land = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for(Object[] obj:result){
                NoShapeOriginalAssetRentLandDetailsDto detailsDto = new NoShapeOriginalAssetRentLandDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetRentLand(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueRent(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteNoShapeOriginalAssetRentLandById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_rent_land  " +
                "where ns_original_asset_rent_land.id_ns_original_asset_rent_land = :idNsRentLand ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idNsRentLand", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<NoShapeOriginalAssetRentLand> findNoShapeOriginalAssetRentLandById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select rentLand.id_ns_original_asset_rent_land, rentLand.id_asset, " +
                "       rentLand.value_rent, rentLand.value_work, " +
                "       rentLand.time_created, rentLand.time_modified " +
                "from ns_original_asset_rent_land rentLand  " +
                "where rentLand.id_ns_original_asset_rent_land = :idRentLand ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idRentLand", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetRentLand rentLand = new NoShapeOriginalAssetRentLand();
                rentLand.setIdNoShapeOriginalAssetRentLand(ValueUtil.getIntegerByObject(obj[0]));
                rentLand.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                rentLand.setValueRent(ValueUtil.getDoubleByObject(obj[2]));
                rentLand.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                rentLand.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                rentLand.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                return Optional.of(rentLand);
            }
        }

        return Optional.empty();
    }
}
