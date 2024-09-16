package com.example.csvccdshustbe.service.assetCurrentUsage.impl;

import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.example.csvccdshustbe.entity.AssetCurrentUsage;
import com.example.csvccdshustbe.repository.assetCurrentUsage.AssetCurrentUsageRepository;
import com.example.csvccdshustbe.service.assetCurrentUsage.AssetCurrentUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AssetCurrentUsageServiceImpl implements AssetCurrentUsageService {

    @Autowired
    AssetCurrentUsageRepository assetCurrentUsageRepository;

    @Override
    public List<AssetCurrentUsage> saveAll(List<AssetCurrentUsage> assetCurrentUsageList) {
        return assetCurrentUsageRepository.saveAll(assetCurrentUsageList);
    }

    @Override
    public void deleteAssetCurrentUsageServiceByIdAsset(Integer idAsset) {
        assetCurrentUsageRepository.deleteAssetCurrentUsageByIdAsset(idAsset);
    }

    @Override
    public List<AssetCurrentUsage> findByIdAsset(Integer idAsset) {
        return assetCurrentUsageRepository.findByIdAsset(idAsset);
    }

    @Override
    public void deleteAssetCurrentUsage(AssetCurrentUsage usage) {
        assetCurrentUsageRepository.delete(usage);
    }

    @Override
    public void save(AssetCurrentUsage assetCurrentUsage) {
        assetCurrentUsageRepository.save(assetCurrentUsage);
    }

    @Override
    public List<AssetCurrentUsageDetailsDto> findAssetCurrentUsageDetailsByIdAsset(Integer idAsset) {
        List<AssetCurrentUsageDetailsDto> detailsDto =
                assetCurrentUsageRepository.findAssetCurrentUsageDetailsDtoByIdAsset(idAsset);
        return detailsDto;
    }
}
