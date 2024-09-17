package com.example.csvccdshustbe.service.assetDepreciation;

import com.example.csvccdshustbe.entity.AssetDepreciation;

public interface AssetDepreciationService {

    AssetDepreciation findAssetDepreciationById(Integer idAssetDepreciation);
    AssetDepreciation findAssetDepreciationByIdAsset(Integer idAsset);
    void deleteAssetDepreciationByIdAsset (Integer idAsset);

    AssetDepreciation save(AssetDepreciation depreciation);
}
