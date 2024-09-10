package com.example.csvccdshustbe.repository.assetOriginalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.repository.assetOriginalOfFormation.AssetOriginalOfFormationRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class AssetOriginalOfFormationRepositoryImpl implements AssetOriginalOfFormationRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<AssetOriginalOfFormDto> findOriginalOfFormationByIdAsset(Integer idAsset) {
        StringBuilder sb = new StringBuilder();
//        sb.append(" ")
        return null;
    }
}
