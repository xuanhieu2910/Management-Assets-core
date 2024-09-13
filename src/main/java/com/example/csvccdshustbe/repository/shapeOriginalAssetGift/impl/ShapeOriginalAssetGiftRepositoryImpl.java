package com.example.csvccdshustbe.repository.shapeOriginalAssetGift.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;
import com.example.csvccdshustbe.repository.shapeOriginalAssetGift.ShapeOriginalAssetGiftRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetGiftRepositoryImpl implements ShapeOriginalAssetGiftRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetGiftDetailsDto> findShapeOriginalAssetGiftDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_gift, shape.id_asset, shape.value_buy, " +
                "       shape.value_work, shape.value_recall_work, shape.value_tax, " +
                "       shape.value_other, shape.time_created, shape.time_modified " +
                "from s_original_asset_gift shape " +
                "where shape.id_s_original_asset_gift  = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                ShapeOriginalAssetGiftDetailsDto dto = new ShapeOriginalAssetGiftDetailsDto();
                dto.setIdShapeOriginalAssetGift(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                dto.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                dto.setValueRecallWork(ValueUtil.getDoubleByObject(obj[4]));
                dto.setValueTax(ValueUtil.getDoubleByObject(obj[5]));
                dto.setValueOther(ValueUtil.getDoubleByObject(obj[6]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();


    }

    @Transactional
    @Modifying
    @Override
    public void deleteShapeOriginalAssetGiftById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_gift sGift " +
                "where sGift.id_s_original_asset_gift = :idSGift ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idSGift", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetGift> findShapeOriginalAssetGiftById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sgift.id_s_original_asset_gift, sgift.id_asset, sgift.value_buy, " +
                "       sgift.value_work, sgift.value_recall_work, sgift.value_tax, " +
                "       sgift.value_other, sgift.time_created, sgift.time_modified " +
                "from s_original_asset_gift sgift " +
                "where sgift.id_s_original_asset_gift = :idSgift ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idSgift", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetGift assetGift = new ShapeOriginalAssetGift();
                assetGift.setIdShapeOriginalAssetGift(ValueUtil.getIntegerByObject(obj[0]));
                assetGift.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetGift.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                assetGift.setValueWork(ValueUtil.getDoubleByObject(obj[3]));
                assetGift.setValueRecallWork(ValueUtil.getDoubleByObject(obj[4]));
                assetGift.setValueTax(ValueUtil.getDoubleByObject(obj[5]));
                assetGift.setValueOther(ValueUtil.getDoubleByObject(obj[6]));
                assetGift.setTimeCreated(ValueUtil.getStringByObject(obj[7]));
                assetGift.setTimeModified(ValueUtil.getStringByObject(obj[8]));
                return Optional.of(assetGift);
            }
        }
        return Optional.empty();
    }
}
