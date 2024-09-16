package com.example.csvccdshustbe.repository.assetOriginalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.AssetOriginalOfFormation;
import com.example.csvccdshustbe.repository.assetOriginalOfFormation.AssetOriginalOfFormationRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AssetOriginalOfFormationRepositoryImpl implements AssetOriginalOfFormationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AssetOriginalOfFormDto> findOriginalOfFormationDtoByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select originalOfFormation.id_original_of_formation, " +
                "       originalOfFormation.name, " +
                "       assetOriginalOfFormation.value " +
                "from original_of_formation originalOfFormation " +
                "    inner join asset_original_of_formation assetOriginalOfFormation " +
                "        on originalOfFormation.id_original_of_formation = assetOriginalOfFormation.id_original_of_formation " +
                "    inner join asset asset on assetOriginalOfFormation.id_asset = asset.id_asset " +
                "where asset.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<AssetOriginalOfFormDto> assetOriginalOfFormDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj: result){
                AssetOriginalOfFormDto dto = new AssetOriginalOfFormDto();
                dto.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj[0]));
                dto.setNameOriginalOfFormation(ValueUtil.getStringByObject(obj[1]));
                dto.setValue(ValueUtil.getStringByObject(obj[2]));
                assetOriginalOfFormDtos.add(dto);
            }
        }
        return assetOriginalOfFormDtos;
    }

    @Override
    public List<AssetOriginalOfFormation> findOriginalOfFormationByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select aoof.id_asset_origin_of_formation, aoof.id_original_of_formation, " +
                "       aoof.id_asset, aoof.time_created, aoof.time_modified, aoof.value " +
                "from asset_original_of_formation aoof  " +
                "where aoof.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        List<Object[]> result = query.getResultList();
        List<AssetOriginalOfFormation> originalOfFormations = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                AssetOriginalOfFormation original = new AssetOriginalOfFormation();
                original.setIdAssetOriginOfFormation(ValueUtil.getIntegerByObject(obj[0]));
                original.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj[1]));
                original.setIdAsset(ValueUtil.getIntegerByObject(obj[2]));
                original.setTimeModified(ValueUtil.getStringByObject(obj[3]));
                original.setTimeModified(ValueUtil.getStringByObject(obj[4]));
                original.setValue(ValueUtil.getStringByObject(obj[5]));
                originalOfFormations.add(original);
            }
        }
        return originalOfFormations;
    }

    @Modifying
    @Transactional
    @Override
    public void deleteByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
        sb.append(" delete from asset_original_of_formation " +
                "where asset_original_of_formation.id_asset = :idAsset ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idAsset", idAsset);
        query.executeUpdate();
    }
}
