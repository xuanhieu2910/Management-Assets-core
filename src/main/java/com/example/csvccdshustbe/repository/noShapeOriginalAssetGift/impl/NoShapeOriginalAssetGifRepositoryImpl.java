package com.example.csvccdshustbe.repository.noShapeOriginalAssetGift.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetGift.NoShapeOriginalAssetGifRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class NoShapeOriginalAssetGifRepositoryImpl implements NoShapeOriginalAssetGifRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<NoShapeOriginalAssetGiftDetailsDto> findShapeOriginalAssetGiftDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_gift, shape.id_asset, " +
                "       shape.value_buy, shape.value_work, shape.value_tax, " +
                "       shape.value_other, shape.time_created, shape.time_modified " +
                "from ns_original_asset_gift shape " +
                "where shape.id_ns_original_asset_gift = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj: result){
                NoShapeOriginalAssetGiftDetailsDto detailsDto = new NoShapeOriginalAssetGiftDetailsDto();
                detailsDto.setIdNoShapeOriginalAssetGift(ValueUtil.getIntegerByObject(obj[0]));
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
}
