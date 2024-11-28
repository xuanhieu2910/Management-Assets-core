package com.example.csvccdshustbe.repository.assetCurrentUsage;

import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;
import com.example.csvccdshustbe.entity.AssetCurrentUsage;

import java.util.List;

public interface AssetCurrentUsageRepositoryCustom {
    void deleteAssetCurrentUsageByIdAsset(Integer idAsset);

    List<AssetCurrentUsage> findByIdAsset(Integer idAsset);

    List<AssetCurrentUsageDetailsDto> findAssetCurrentUsageDetailsDtoByIdAsset(Integer idAsset);

    CurrentUsageReport08aDto findAllCurrentUsageAssetGroundInReport(List<Integer> idsDepartment);

    CurrentUsageReport08aDto findAllCurrentUsageAssetHouseInReport(List<Integer> idsDepartment);

    CurrentUsageReport08aDto findAllCurrentUsageAssetCarInReport(List<Integer> idsDepartment);

    CurrentUsageReport08aDto findAllCurrentUsageAssetOtherInReport(List<Integer> idsDepartment);

}
