package com.example.csvccdshustbe.service.modules.assetModules.impl;

import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.entity.AssetModules;
import com.example.csvccdshustbe.response.assetModules.AssetModulesRepository;
import com.example.csvccdshustbe.service.modules.assetModules.AssetModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class AssetModulesServiceImpl implements AssetModulesService {


    @Autowired
    AssetModulesRepository assetModulesRepository;

    @Override
    public AssetModules save(AssetModules modules) {
        return assetModulesRepository.save(modules);
    }

    @Override
    public List<AssetModules> findAllAssetModulesByIdAsset(Integer idAsset) {
        return assetModulesRepository.findAllAssetModulesByIdAsset(idAsset);
    }

    @Override
    public List<BluePrintAssetModulesDto> findBluePrintAssetModulesDtoByIdAsset(Integer idAsset) {
        List<BluePrintAssetModulesDto> assetModulesDtos = assetModulesRepository.findBluePrintAssetModulesDtoByIdAsset(idAsset);
        if (!CollectionUtils.isEmpty(assetModulesDtos)) {
            throw new NotFoundException("Don't exits asset modules by id asset!");
        }
        return assetModulesDtos;
    }

    @Override
    public void deleteAssetModulesByAssetModules(List<AssetModules> assetModules) {
        assetModulesRepository.deleteAll(assetModules);
    }

    @Override
    public void deleteAssetModulesByIdInstanceAndIdModule(Integer idInstance, Integer idModule) {
        assetModulesRepository.deleteAssetModulesByIdInstanceAndIdModule(idInstance, idModule);
    }
}
