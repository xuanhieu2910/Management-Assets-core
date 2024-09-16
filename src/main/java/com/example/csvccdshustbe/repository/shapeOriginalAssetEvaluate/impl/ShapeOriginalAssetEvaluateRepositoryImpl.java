package com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetEvaluateDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate.ShapeOriginalAssetEvaluateRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetEvaluateRepositoryImpl implements ShapeOriginalAssetEvaluateRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetEvaluateDetailsDto> findShapeOriginalAssetEvaluateDetailsDtoBuyId(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_evaluate, shape.id_asset, " +
                "       shape.value_buy, shape.value_tax, shape.value_other, " +
                "       shape.time_created, shape.time_modified " +
                "from s_original_asset_evaluate shape " +
                "where shape.id_s_original_asset_evaluate = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetEvaluateDetailsDto detailsDto = new ShapeOriginalAssetEvaluateDetailsDto();
                detailsDto.setIdShapeOriginalAssetEvaluate(ValueUtil.getIntegerByObject(obj[0]));
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

    @Transactional
    @Modifying
    @Override
    public void deleteOriginalAssetEvaluateById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_evaluate  " +
                "where s_original_asset_evaluate.id_s_original_asset_evaluate = :idsEva ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsEva", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetEvaluate> findShapeOriginalAssetEvaluateById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select eva.id_s_original_asset_evaluate, eva.id_asset, eva.value_buy, " +
                "       eva.value_tax, eva.value_other, eva.time_created, eva.time_modified " +
                "from s_original_asset_evaluate eva " +
                "where eva.id_s_original_asset_evaluate = :idEvaluate ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idEvaluate", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                ShapeOriginalAssetEvaluate evaluate = new ShapeOriginalAssetEvaluate();
                evaluate.setIdShapeOriginalAssetEvaluate(ValueUtil.getIntegerByObject(obj[0]));
                evaluate.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                evaluate.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                evaluate.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                evaluate.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                evaluate.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                evaluate.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(evaluate);
            }
        }
        return Optional.empty();
    }
}
