package com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer.ShapeOriginalAssetTransferRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetTransferRepositoryImpl implements ShapeOriginalAssetTransferRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetTransferDetailsDto> findShapeOriginalAssetTransferDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_transfer, shape.id_asset, shape.value_buy, " +
                "       shape.value_work, shape.value_recall_work, shape.value_tax, " +
                "       shape.value_other, shape.time_created, shape.time_modified " +
                "from s_original_asset_transfer shape " +
                "where shape.id_s_original_asset_transfer = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                ShapeOriginalAssetTransferDetailsDto detailsDto = new ShapeOriginalAssetTransferDetailsDto();
                detailsDto.setIdShapeOriginalAssetTransfer(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setValueRecallWork(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setValueTax(ValueUtil.getDoubleByObject(obj[5]));
                detailsDto.setValueOther(ValueUtil.getDoubleByObject(obj[6]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }
}
