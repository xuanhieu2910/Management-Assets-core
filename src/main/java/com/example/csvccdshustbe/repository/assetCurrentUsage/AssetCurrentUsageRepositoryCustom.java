package com.example.csvccdshustbe.repository.assetCurrentUsage;

import com.example.csvccdshustbe.entity.AssetCurrentUsage;

import java.util.List;

public interface AssetCurrentUsageRepositoryCustom {
    void deleteAssetCurrentUsageByIdAsset(Integer idAsset);

    List<AssetCurrentUsage> findByIdAsset(Integer idAsset);
}
