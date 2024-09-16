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
    AssetOriginalOfFormationRepository assetOriginalOfFormationRepository;

    @Override
    public AssetOriginalOfFormation save(AssetOriginalOfFormation assetOriginalOfFormation) {
        return assetOriginalOfFormationRepository.save(assetOriginalOfFormation);
    }

    @Override
    public List<AssetOriginalOfFormation> saveAll(List<AssetOriginalOfFormation> assetOriginalOfFormation) {
        return assetOriginalOfFormationRepository.saveAll(assetOriginalOfFormation);
    }

    @Override
    public List<AssetOriginalOfFormDto> findOriginalOfFormationDtoByIdAsset(Integer idAsset) {
        return assetOriginalOfFormationRepository.findOriginalOfFormationDtoByIdAsset(idAsset);
    }

    @Override
    public List<AssetOriginalOfFormation> findOriginalOfFormationByIdAsset(Integer idAsset) {
        return assetOriginalOfFormationRepository.findOriginalOfFormationByIdAsset(idAsset);
    }

    @Override
    public void deleteAssetOriginalOfFormation(AssetOriginalOfFormation original) {
        assetOriginalOfFormationRepository.delete(original);
    }

    @Override
    public void deleteByIdAsset(Integer idAsset) {
        assetOriginalOfFormationRepository.deleteByIdAsset(idAsset);
    }
}
