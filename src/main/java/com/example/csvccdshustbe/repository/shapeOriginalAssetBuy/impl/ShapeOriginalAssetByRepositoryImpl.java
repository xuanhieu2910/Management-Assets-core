package com.example.csvccdshustbe.repository.shapeOriginalAssetBuy.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;
import com.example.csvccdshustbe.repository.shapeOriginalAssetBuy.ShapeOriginalAssetByRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetByRepositoryImpl implements ShapeOriginalAssetByRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetBuyDetailsDto> findOriginalAssetBuyDetailsDtoById(Integer idAssetBuy) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shapeBuy.id_s_original_asset_buy, shapeBuy.id_asset, shapeBuy.value_buy,   " +
                "       shapeBuy.value_discount, shapeBuy.value_work, shapeBuy.value_recall_work,   " +
                "       shapeBuy.value_tax, shapeBuy.value_other, shapeBuy.id_method_buy_asset,   " +
                "       shapeBuy.id_type_buy_asset, shapeBuy.time_created, shapeBuy.time_modified,   " +
                "       me.title nameMethodBuyAsset, ty.title nameTypeBuyAsset   " +
                "from s_original_asset_buy shapeBuy   " +
                "    left join method_buy_asset me on shapeBuy.id_method_buy_asset = me.id_method_buy_asset   " +
                "    left join type_buy_asset ty on shapeBuy.id_type_buy_asset = ty.id_type_buy_asset   " +
                "where shapeBuy.id_s_original_asset_buy = :idShapeBuy ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShapeBuy", idAssetBuy);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                ShapeOriginalAssetBuyDetailsDto dto = new ShapeOriginalAssetBuyDetailsDto();
                dto.setIdShapeOriginalAssetBuy(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                dto.setValueDiscount(ValueUtil.getDoubleByObject(obj[3]));
                dto.setValueWork(ValueUtil.getDoubleByObject(obj[4]));
                dto.setValueRecallWork(ValueUtil.getDoubleByObject(obj[5]));
                dto.setValueTax(ValueUtil.getDoubleByObject(obj[6]));
                dto.setValueOther(ValueUtil.getDoubleByObject(obj[7]));
                dto.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(obj[8]));
                dto.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(obj[9]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[10]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[11]));
                dto.setNameMethodBuyAsset(ValueUtil.getStringByObject(obj[12]));
                dto.setNameTypeBuyAsset(ValueUtil.getStringByObject(obj[13]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteShapeOriginalAssetById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from s_original_asset_buy sori  " +
                "where sori.id_s_original_asset_buy = :idSori ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idSori", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetBuy> findShapeOriginalAssetBuyById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select sBuy.id_s_original_asset_buy, sBuy.id_asset, sBuy.value_buy, " +
                "       sBuy.value_discount, sBuy.value_work, sBuy.value_recall_work, " +
                "       sBuy.value_tax, sBuy.value_other, sBuy.id_method_buy_asset, " +
                "       sBuy.id_type_buy_asset, sBuy.time_created, sBuy.time_modified " +
                "from s_original_asset_buy sBuy " +
                "where sBuy.id_s_original_asset_buy = :idSBuy ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idSBuy", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object [] obj : result){
                ShapeOriginalAssetBuy assetBuy = new ShapeOriginalAssetBuy();
                assetBuy.setIdShapeOriginalAssetBuy(ValueUtil.getIntegerByObject(obj[0]));
                assetBuy.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetBuy.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                assetBuy.setValueDiscount(ValueUtil.getDoubleByObject(obj[3]));
                assetBuy.setValueWork(ValueUtil.getDoubleByObject(obj[4]));
                assetBuy.setValueRecallWork(ValueUtil.getDoubleByObject(obj[5]));
                assetBuy.setValueTax(ValueUtil.getDoubleByObject(obj[6]));
                assetBuy.setValueOther(ValueUtil.getDoubleByObject(obj[7]));
                assetBuy.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(obj[8]));
                assetBuy.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(obj[9]));
                assetBuy.setTimeCreated(ValueUtil.getStringByObject(obj[10]));
                assetBuy.setTimeModified(ValueUtil.getStringByObject(obj[11]));
                return Optional.of(assetBuy);
            }
        }
        return Optional.empty();
    }
}
