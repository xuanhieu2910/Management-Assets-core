package com.example.csvccdshustbe.service.assetProcess.impl;

import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.repository.assetProcess.AssetProcessRepository;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetProcessServiceImpl implements AssetProcessService {

    @Autowired
    AssetProcessRepository assetProcessRepository;

    @Override
    public List<AssetProcess> saveListAssetProcess(List<AssetProcess> assetProcessList) {
        return assetProcessRepository.saveAll(assetProcessList);
    }
}
