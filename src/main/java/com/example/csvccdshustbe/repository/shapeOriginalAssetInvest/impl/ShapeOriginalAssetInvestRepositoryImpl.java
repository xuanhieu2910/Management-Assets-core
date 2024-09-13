package com.example.csvccdshustbe.repository.shapeOriginalAssetInvest.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetInvestDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;
import com.example.csvccdshustbe.repository.shapeOriginalAssetInvest.ShapeOriginalAssetInvestRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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


    @Modifying
    @Transactional
    @Override
    public void deleteShapeOriginalAssetInvestById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_invest sinvest " +
                "where sinvest.id_s_original_asset_invest = :idsInvest ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsInvest", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetInvest> findOriginalAssetInvestById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select invest.id_s_original_asset_invest, invest.id_asset, " +
                "       invest.value_buy, invest.time_created, invest.time_modified  " +
                "from s_original_asset_invest invest " +
                "where invest.id_s_original_asset_invest = :idInvest ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idInvest", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetInvest assetInvest = new ShapeOriginalAssetInvest();
                assetInvest.setIdShapeOriginalAssetInvest(ValueUtil.getIntegerByObject(obj[0]));
                assetInvest.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetInvest.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                assetInvest.setTimeCreated(ValueUtil.getStringByObject(obj[3]));
                assetInvest.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                return Optional.of(assetInvest);
            }
        }
        return Optional.empty();
    }
}
