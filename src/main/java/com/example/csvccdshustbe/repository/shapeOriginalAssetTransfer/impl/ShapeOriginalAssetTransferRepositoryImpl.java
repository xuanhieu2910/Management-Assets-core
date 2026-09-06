package com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer.ShapeOriginalAssetTransferRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
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

    @Transactional
    @Modifying
    @Override
    public void deleteShapeOriginalAssetTransferById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_transfer  " +
                "where s_original_asset_transfer.id_s_original_asset_transfer = :idstransfer ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idstransfer", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetTransfer> findShapeOriginalAssetTransferById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select transfer.id_s_original_asset_transfer, transfer.id_asset, transfer.value_buy, " +
                "       transfer.value_work, transfer.value_recall_work, transfer.value_tax, " +
                "       transfer.value_other, transfer.time_created, transfer.time_modified " +
                "from s_original_asset_transfer transfer " +
                "where transfer.id_s_original_asset_transfer = :idTransfer ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTransfer", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetTransfer assetTransfer = new ShapeOriginalAssetTransfer();
                assetTransfer.setIdShapeOriginalAssetTransfer(ValueUtil.getIntegerByObject(obj[0]));
                assetTransfer.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetTransfer.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                assetTransfer.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                assetTransfer.setValueRecallWork(ValueUtil.getDoubleByObject(obj[4]));
                assetTransfer.setValueTax(ValueUtil.getDoubleByObject(obj[5]));
                assetTransfer.setValueOther(ValueUtil.getDoubleByObject(obj[6]));
                assetTransfer.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                assetTransfer.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(assetTransfer);
            }
        }
        return Optional.empty();
    }
}
