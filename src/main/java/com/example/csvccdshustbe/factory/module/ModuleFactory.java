package com.example.csvccdshustbe.factory.module;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.IModules;

import java.util.Map;

public interface ModuleFactory {

    IModules createModule(Map<String, Object> mapModuleCreate);
    IModules updateModule(Map<String,Object> mapModuleUpdate, IModules iModules);
    IModules copyModule(AssetModulesDto assetModulesDto, Integer idAsset);
}
