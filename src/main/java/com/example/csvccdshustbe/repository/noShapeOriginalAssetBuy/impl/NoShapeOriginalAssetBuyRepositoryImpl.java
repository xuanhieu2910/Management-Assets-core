package com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy.NoShapeOriginalAssetBuyRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class NoShapeOriginalAssetBuyRepositoryImpl implements NoShapeOriginalAssetBuyRepositoryCustom {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public Optional<NoShapeOriginalAssetBuyDetailsDto> findNoShapeOriginalAssetBuyDetailsBuyId(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_buy, shape.id_asset,  " +
                "       shape.value_buy, shape.value_tax, shape.value_other,  " +
                "       shape.time_created, shape.time_modified,  " +
                "       shape.id_method_buy_asset, shape.id_type_buy_asset, " +
                "       me.title nameMethodBuyAsset, ty.title nameTypeBuyAsset " +
                "from ns_original_asset_buy shape " +
                "    left join method_buy_asset me on shape.id_method_buy_asset = me.id_method_buy_asset " +
                "    left join type_buy_asset ty on shape.id_type_buy_asset = ty.id_type_buy_asset " +
                "where shape.id_ns_original_asset_buy = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetBuyDetailsDto detailsDto = new NoShapeOriginalAssetBuyDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetBuy(ValueUtil.getIntegerByObject(obj[0]));
                detailsDto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                detailsDto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                detailsDto.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                detailsDto.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                detailsDto.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                detailsDto.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                detailsDto.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(obj[7]));
                detailsDto.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(obj[8]));
                detailsDto.setNameMethodBuyAsset(ValueUtil.getStringByObject(obj[9]));
                detailsDto.setNameTypeBuyAsset(ValueUtil.getStringByObject(obj[10]));
                return Optional.of(detailsDto);
            }
        }
        return Optional.empty();
    }

    @Modifying
    @Transactional
    @Override
    public void deleteNoShapeOriginalAssetBuyById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_buy nsBuy " +
                "where nsBuy.id_ns_original_asset_buy = :idnsBuy ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idnsBuy", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<NoShapeOriginalAssetBuy> findNoShapeOriginalAssetBuyById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select assetBuy.id_ns_original_asset_buy, assetBuy.id_asset, " +
                "       assetBuy.value_buy, assetBuy.value_tax, assetBuy.value_other, " +
                "       assetBuy.time_created, assetBuy.time_modified, assetBuy.id_method_buy_asset, " +
                "       assetBuy.id_type_buy_asset " +
                "from ns_original_asset_buy assetBuy " +
                "where assetBuy.id_ns_original_asset_buy = :idAssetBuy ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAssetBuy", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetBuy assetBuy = new NoShapeOriginalAssetBuy();
                assetBuy.setIdNoShapeOriginalAssetBuy(ValueUtil.getIntegerByObject(obj[0]));
                assetBuy.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                assetBuy.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                assetBuy.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                assetBuy.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                assetBuy.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                assetBuy.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                assetBuy.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(obj[7]));
                assetBuy.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(obj[8]));
                return Optional.of(assetBuy);
            }
        }
        return Optional.empty();
    }
}
