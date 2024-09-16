package com.example.csvccdshustbe.service.assetCurrentUsage;

import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.example.csvccdshustbe.entity.AssetCurrentUsage;

import java.util.List;

public interface AssetCurrentUsageService {

    List<AssetCurrentUsage> saveAll(List<AssetCurrentUsage> assetCurrentUsageList);

    void deleteAssetCurrentUsageServiceByIdAsset(Integer idAsset);

    List<AssetCurrentUsage> findByIdAsset(Integer idAsset);

    void deleteAssetCurrentUsage(AssetCurrentUsage usage);

    void save(AssetCurrentUsage assetCurrentUsage);

    List<AssetCurrentUsageDetailsDto> findAssetCurrentUsageDetailsByIdAsset(Integer idAsset);
}
