package com.example.csvccdshustbe.repository.shapeOriginalAssetInvest.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetInvestDetailsDto;
import com.example.csvccdshustbe.repository.shapeOriginalAssetInvest.ShapeOriginalAssetInvestRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetInvestRepositoryImpl implements ShapeOriginalAssetInvestRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetInvestDetailsDto> findOriginalAssetInvestDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_invest, shape.id_asset, " +
                "       shape.value_buy, shape.time_created, " +
                "       shape.time_modified " +
                "from s_original_asset_invest shape " +
                "where shape.id_s_original_asset_invest = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                ShapeOriginalAssetInvestDetailsDto detailsDto = new ShapeOriginalAssetInvestDetailsDto();
                detailsDto.setIdShapeOriginalAssetInvest(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }
}
