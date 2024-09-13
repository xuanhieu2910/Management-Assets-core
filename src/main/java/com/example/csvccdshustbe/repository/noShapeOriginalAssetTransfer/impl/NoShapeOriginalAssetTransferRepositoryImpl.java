package com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer.NoShapeOriginalAssetTransferRepositoryCustom;
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

public class NoShapeOriginalAssetTransferRepositoryImpl implements NoShapeOriginalAssetTransferRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<NoShapeOriginalAssetTransferDetailsDto> findNoShapeOriginalAssetTransferDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_transfer, shape.id_asset, shape.value_buy, " +
                "       shape.value_work, shape.value_tax, shape.value_other, shape.time_created, " +
                "       shape.time_modified " +
                "from ns_original_asset_transfer shape  " +
                "where shape.id_ns_original_asset_transfer = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj:result){
                NoShapeOriginalAssetTransferDetailsDto detailsDto = new NoShapeOriginalAssetTransferDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetTransfer(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setValueTax(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setValueOther(ValueUtil.getDoubleByObject(obj[5]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }


    @Modifying
    @Transactional
    @Override
    public void deleteNoShapeOriginalAssetTransferById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_transfer nsTransfer " +
                "where nsTransfer.id_ns_original_asset_transfer = :idnsTransfer ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idnsTransfer", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<NoShapeOriginalAssetTransfer> findNoShapeOriginalAssetTransferById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select transfer.id_ns_original_asset_transfer, transfer.id_asset, " +
                "       transfer.value_buy, transfer.value_work, transfer.value_tax, " +
                "       transfer.value_other, transfer.time_created, transfer.time_modified " +
                "from ns_original_asset_transfer transfer " +
                "where transfer.id_ns_original_asset_transfer = :idTransfer ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idTransfer", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result) {
                NoShapeOriginalAssetTransfer assetTransfer = new NoShapeOriginalAssetTransfer();
                assetTransfer.setIdNoShapeOriginalAssetTransfer(ValueUtil.getIntegerByObject(obj[0]));
                assetTransfer.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetTransfer.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                assetTransfer.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                assetTransfer.setValueTax(ValueUtil.getDoubleByObject(obj[4]));
                assetTransfer.setValueOther(ValueUtil.getDoubleByObject(obj[5]));
                assetTransfer.setTimeCreated(ValueUtil.getStringByObject(obj[6]));
                assetTransfer.setTimeModified(ValueUtil.getStringByObject(obj[7]));
                return Optional.of(assetTransfer);
            }
        }
        return Optional.empty();
    }
}
