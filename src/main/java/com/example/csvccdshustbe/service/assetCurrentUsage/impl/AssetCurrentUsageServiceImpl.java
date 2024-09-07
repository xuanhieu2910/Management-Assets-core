package com.example.csvccdshustbe.service.assetCurrentUsage.impl;

import com.example.csvccdshustbe.entity.AssetCurrentUsage;
import com.example.csvccdshustbe.repository.assetCurrentUsage.AssetCurrentUsageRepository;
import com.example.csvccdshustbe.service.assetCurrentUsage.AssetCurrentUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetCurrentUsageServiceImpl implements AssetCurrentUsageService {

    @Autowired
    AssetCurrentUsageRepository assetCurrentUsageRepository;

    @Override
    public List<AssetCurrentUsage> saveAll(List<AssetCurrentUsage> assetCurrentUsageList) {
        return assetCurrentUsageRepository.saveAll(assetCurrentUsageList);
    }
}
