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
    public void deleteAssetDepreciationByIdAsset(Integer idAsset) {
        assetDepreciationRepository.deleteAssetDepreciationByIdAsset(idAsset);
    }


}
