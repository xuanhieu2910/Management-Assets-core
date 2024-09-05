package com.example.csvccdshustbe.service.modules.assetModules.impl;

import com.example.csvccdshustbe.entity.AssetModules;
import com.example.csvccdshustbe.response.assetModules.AssetModulesRepository;
import com.example.csvccdshustbe.service.modules.assetModules.AssetModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetModulesServiceImpl implements AssetModulesService {


    @Autowired
    AssetModulesRepository assetModulesRepository;

    @Override
    public AssetModules save(AssetModules modules) {
        return assetModulesRepository.save(modules);
    }
}
