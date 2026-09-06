package com.example.csvccdshustbe.repository.noShapeOriginalAssetOther.impl;


import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetOther;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetOther.NoShapeOriginalAssetOtherRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public class NoShapeOriginalAssetOtherRepositoryImpl implements NoShapeOriginalAssetOtherRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Modifying
    @Override
    public void deleteNoShapeOriginalAssetOtherById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from ns_original_asset_other  " +
                "where ns_original_asset_other.id_ns_original_asset_other = :idsactor ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idsactor", idInstance);
        query.executeUpdate();
    }

    @Override
    public Optional<NoShapeOriginalAssetOtherDetailsDto> findNoOriginalConnectActorDetailsById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_other, " +
                "       shape.id_asset, shape.time_created, shape.time_modified " +
                "from ns_original_asset_other shape " +
                "where shape.id_ns_original_asset_other = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetOtherDetailsDto dto = new NoShapeOriginalAssetOtherDetailsDto();
                dto.setIdNoShapeOriginalAssetOther(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<NoShapeOriginalAssetOther> findNoShapeOriginalAssetOtherById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_ns_original_asset_other, " +
                "       shape.id_asset, shape.time_created, shape.time_modified " +
                "from ns_original_asset_other shape " +
                "where shape.id_ns_original_asset_other = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                NoShapeOriginalAssetOther dto = new NoShapeOriginalAssetOther();
                dto.setIdNoShapeOriginalAssetOther(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }
}
