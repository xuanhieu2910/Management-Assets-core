package com.example.csvccdshustbe.service.assetDepreciation.impl;

import com.example.csvccdshustbe.entity.AssetDepreciation;
import com.example.csvccdshustbe.repository.assetDepreciation.AssetDepreciationRepository;
import com.example.csvccdshustbe.service.assetDepreciation.AssetDepreciationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class AssetDepreciationServiceImpl implements AssetDepreciationService {

    @Autowired
    AssetDepreciationRepository assetDepreciationRepository;

    @Override
    public AssetDepreciation findAssetDepreciationById(Integer idAssetDepreciation) {
        Optional<AssetDepreciation> assetDepreciation =
                assetDepreciationRepository.findAssetDepreciationById(idAssetDepreciation);
        if (assetDepreciation.isEmpty()){
            throw new NotFoundException("Don't exits asset depreciation!");
        }
        return assetDepreciation.get();
    }

    @Override
    public AssetDepreciation findAssetDepreciationByIdAsset(Integer idAsset) {
        Optional<AssetDepreciation> assetDepreciation =
                assetDepreciationRepository.findAssetDepreciationByIdAsset(idAsset);
        if (assetDepreciation.isEmpty()){
            throw new NotFoundException("Don't exits asset depreciation by id asset");
        }
        return null;
    }

    @Override
    public void deleteAssetDepreciationByIdAsset(Integer idAsset) {
        assetDepreciationRepository.deleteAssetDepreciationByIdAsset(idAsset);
    }

    @Override
    public AssetDepreciation save(AssetDepreciation depreciation) {
        return assetDepreciationRepository.save(depreciation);
    }


}
