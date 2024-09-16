package com.example.csvccdshustbe.repository.assetCurrentUsage;

import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.example.csvccdshustbe.entity.AssetCurrentUsage;

import java.util.List;
import java.util.Optional;

public interface AssetCurrentUsageRepositoryCustom {
    void deleteAssetCurrentUsageByIdAsset(Integer idAsset);

    List<AssetCurrentUsage> findByIdAsset(Integer idAsset);

    List<AssetCurrentUsageDetailsDto> findAssetCurrentUsageDetailsDtoByIdAsset(Integer idAsset);
}
