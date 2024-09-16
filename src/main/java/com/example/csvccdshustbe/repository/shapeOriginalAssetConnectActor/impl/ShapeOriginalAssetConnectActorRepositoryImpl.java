package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectActor.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectActorDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import com.example.csvccdshustbe.repository.shapeOriginalAssetConnectActor.ShapeOriginalAssetConnectActorRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetConnectActorRepositoryImpl implements ShapeOriginalAssetConnectActorRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<ShapeOriginalAssetConnectActorDetailsDto> findShapeOriginalAssetConnectActorDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_connect_actor, " +
                "       shape.id_asset, shape.time_created, shape.time_modified " +
                "from s_original_asset_connect_actor shape " +
                "where shape.id_s_original_asset_connect_actor = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetConnectActorDetailsDto dto = new ShapeOriginalAssetConnectActorDetailsDto();
                dto.setIdShapeOriginalAssetConnectActor(ValueUtil.getIntegerByObject(obj[0]));
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
    public void deleteShapeOriginalAssetConnectActorById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_connect_actor  " +
                "where s_original_asset_connect_actor.id_s_original_asset_connect_actor = :idsactor ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.executeUpdate();
    }

    @Override
    public Optional<ShapeOriginalAssetConnectActor> findShapeOriginalAssetConnectActorById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select actor.id_s_original_asset_connect_actor,  actor.id_asset, " +
                "       actor.time_created,  actor.time_modified " +
                "from s_original_asset_connect_actor actor " +
                "where actor.id_s_original_asset_connect_actor = :idActor ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idActor", idInstance);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetConnectActor actor = new ShapeOriginalAssetConnectActor();
                actor.setIdShapeOriginalAssetConnectActor(ValueUtil.getIntegerByObject(obj[0]));
                actor.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                actor.setTimeCreated(ValueUtil.getStringByObject(obj[2]));
                actor.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                return Optional.of(actor);
            }
        }
        return Optional.empty();
    }
}
