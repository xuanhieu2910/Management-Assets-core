package com.example.csvccdshustbe.service.assetDepreciation;

import com.example.csvccdshustbe.entity.AssetDepreciation;

public interface AssetDepreciationService {

    AssetDepreciation findAssetDepreciationById(Integer idAssetDepreciation);
    void deleteAssetDepreciationByIdAsset (Integer idAsset);
}
