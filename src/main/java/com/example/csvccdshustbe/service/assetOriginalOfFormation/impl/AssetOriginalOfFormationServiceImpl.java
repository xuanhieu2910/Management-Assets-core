package com.example.csvccdshustbe.service.assetOriginalOfFormation.impl;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.AssetOriginalOfFormation;
import com.example.csvccdshustbe.repository.assetOriginalOfFormation.AssetOriginalOfFormationRepository;
import com.example.csvccdshustbe.service.assetOriginalOfFormation.AssetOriginalOfFormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetOriginalOfFormationServiceImpl implements AssetOriginalOfFormationService {

    @Autowired
    AssetOriginalOfFormationRepository originalOfFormationRepository;

    @Override
    public AssetOriginalOfFormation save(AssetOriginalOfFormation assetOriginalOfFormation) {
        return originalOfFormationRepository.save(assetOriginalOfFormation);
    }

    @Override
    public List<AssetOriginalOfFormation> saveAll(List<AssetOriginalOfFormation> assetOriginalOfFormation) {
        return originalOfFormationRepository.saveAll(assetOriginalOfFormation);
    }

    @Override
    public List<AssetOriginalOfFormDto> findOriginalOfFormationByIdAsset(Integer idAsset) {
        return null;
    }
}
