package com.example.csvccdshustbe.repository.shapeOriginalAssetOther.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectActorDetailsDto;
import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetOther;
import com.example.csvccdshustbe.repository.shapeOriginalAssetOther.ShapeOriginalAssetOtherRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetOtherRepositoryImpl implements ShapeOriginalAssetOtherRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetOtherDetailsDto> findOriginalConnectActorDetailsDtoById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_other, " +
                "       shape.id_asset, shape.time_created, shape.time_modified " +
                "from s_original_asset_other shape " +
                "where shape.id_s_original_asset_other = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetOtherDetailsDto dto = new ShapeOriginalAssetOtherDetailsDto();
                dto.setIdShapeOriginalAssetOther(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
    @Transactional
    @Modifying
    @Override
    public void deleteShapeOriginalAssetOtherById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_other  " +
                "where s_original_asset_other.id_s_original_asset_other = :idsactor ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsactor", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetOther> findOriginalConnectActorDetailsById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_other, " +
                "       shape.id_asset, shape.time_created, shape.time_modified " +
                "from s_original_asset_other shape " +
                "where shape.id_s_original_asset_other = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetOther dto = new ShapeOriginalAssetOther();
                dto.setIdShapeOriginalAssetOther(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
