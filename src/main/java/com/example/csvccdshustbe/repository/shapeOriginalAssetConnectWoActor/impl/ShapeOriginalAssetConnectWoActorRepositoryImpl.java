package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectWoActorDetailsDto;
import com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor.ShapeOriginalAssetConnectWoActorRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ShapeOriginalAssetConnectWoActorRepositoryImpl implements ShapeOriginalAssetConnectWoActorRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Optional<ShapeOriginalAssetConnectWoActorDetailsDto> findShapeOriginalAssetConnectWoActorDetailsDtoById(Integer id) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select shape.id_s_original_asset_connect_wo_actor, shape.id_asset, " +
                "       shape.value_buy, shape.value_tax, shape.value_other, " +
                "       shape.time_created, shape.time_modified " +
                "from s_original_asset_connect_wo_actor shape " +
                "where shape.id_s_original_asset_connect_wo_actor = :idShape ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idShape", id);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                ShapeOriginalAssetConnectWoActorDetailsDto dto = new ShapeOriginalAssetConnectWoActorDetailsDto();
                dto.setIdShapeOriginalAssetConnectWoActor(ValueUtil.getIntegerByObject(obj[0]));
                dto.setIdAsset(ValueUtil.getIntegerByObject(obj[1]));
                dto.setValueBuy(ValueUtil.getDoubleByObject(obj[2]));
                dto.setValueTax(ValueUtil.getDoubleByObject(obj[3]));
                dto.setValueOther(ValueUtil.getDoubleByObject(obj[4]));
                dto.setTimeCreated(ValueUtil.getStringByObject(obj[5]));
                dto.setTimeModified(ValueUtil.getStringByObject(obj[6]));
                return Optional.of(dto);
            }
        }
        return Optional.empty();
    }


    @Modifying
    @Transactional
    @Override
    public void deleteShapeOriginalAssetConnectWoActorById(Integer idInstance) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from s_original_asset_connect_wo_actor swoactor " +
                "where swoactor.id_s_original_asset_connect_wo_actor = :swoactor ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("swoactor", idInstance);
        query.executeUpdate();
    }
}
