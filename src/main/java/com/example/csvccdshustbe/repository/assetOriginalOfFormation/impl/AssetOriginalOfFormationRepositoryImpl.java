package com.example.csvccdshustbe.repository.assetOriginalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.repository.assetOriginalOfFormation.AssetOriginalOfFormationRepositoryCustom;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class AssetOriginalOfFormationRepositoryImpl implements AssetOriginalOfFormationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AssetOriginalOfFormDto> findOriginalOfFormationByIdAsset(Integer idAsset) {
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
}
