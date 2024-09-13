package com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetUseLandDetailsDto;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand.NoShapeOriginalAssetUseLandRepositoryCustom;
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

public class NoShapeOriginalAssetUseLandRepositoryImpl implements NoShapeOriginalAssetUseLandRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<NoShapeOriginalAssetUseLandDetailsDto> findNoShapeOriginalAssetUseLandById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_use_land, shape.id_asset, " +
                "       shape.value_buy, shape.value_tax, shape.value_other, " +
                "       shape.time_created, shape.time_modified " +
                "from ns_original_asset_use_land shape " +
                "where shape.id_ns_original_asset_use_land = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetUseLandDetailsDto detailsDto = new NoShapeOriginalAssetUseLandDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetUseLand(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteNoShapeOriginalAssetUseLandById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_use_land nsUseLand " +
                "where nsUseLand.id_ns_original_asset_use_land = :idnsUseLand ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idnsUseLand", idInstance);
        query.executeUpdate();
    }
}
