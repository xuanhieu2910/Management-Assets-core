package com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetEvaluateDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate.NoShapeOriginalAssetEvaluateRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Repository
public class NoShapeOriginalAssetEvaluateRepositoryImpl implements NoShapeOriginalAssetEvaluateRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<NoShapeOriginalAssetEvaluateDetailsDto> findNoShapeOriginalAssetEvaluateDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_evaluate, shape.id_asset, " +
                "       shape.value_buy, shape.value_tax, shape.value_other, " +
                "       shape.time_created, shape.time_modified " +
                "from ns_original_asset_evaluate shape " +
                "where shape.id_ns_original_asset_evaluate = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetEvaluateDetailsDto detailsDto = new NoShapeOriginalAssetEvaluateDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetEvaluate(ValueUtil.getIntegerByObject(obj[0]));
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
    public void deleteNoShapeOriginalAssetEvaluateById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_evaluate  " +
                "where ns_original_asset_evaluate.id_ns_original_asset_evaluate = :idNsEva ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idNsEva", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<NoShapeOriginalAssetEvaluate> findNoShapeOriginalAssetEvaluateById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select evaluate.id_ns_original_asset_evaluate, evaluate.id_asset, " +
                "       evaluate.value_buy, evaluate.value_tax, evaluate.value_other, " +
                "       evaluate.time_created, evaluate.time_modified " +
                "from ns_original_asset_evaluate evaluate " +
                "where evaluate.id_ns_original_asset_evaluate = :idEvaluate ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idEvaluate", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                NoShapeOriginalAssetEvaluate evaluate = new NoShapeOriginalAssetEvaluate();
                evaluate.setIdNoShapeOriginalAssetEvaluate(ValueUtil.getIntegerByObject(obj[0]));
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
