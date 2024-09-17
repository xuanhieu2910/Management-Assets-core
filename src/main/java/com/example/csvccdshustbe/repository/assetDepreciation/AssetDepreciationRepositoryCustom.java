package com.example.csvccdshustbe.repository.assetDepreciation;

import com.example.csvccdshustbe.entity.AssetDepreciation;

import java.util.Optional;

public interface AssetDepreciationRepositoryCustom {
    Optional<AssetDepreciation> findAssetDepreciationById(Integer idAssetDepreciation);
    void deleteAssetDepreciationByIdAsset(Integer idAsset);

}
