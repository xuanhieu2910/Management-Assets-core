package com.example.csvccdshustbe.service.modules.assetModules;

import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.entity.AssetModules;

import java.util.List;

public interface AssetModulesService {

    AssetModules save(AssetModules modules);

    List<AssetModules> findAllAssetModulesByIdAsset(Integer idAsset);

    List<BluePrintAssetModulesDto> findBluePrintAssetModulesDtoByIdAsset(Integer idAsset);

    void deleteAssetModulesByAssetModules(List<AssetModules> assetModules);

    void deleteAssetModulesByIdInstanceAndIdModule(Integer idInstance, Integer idModule);
}
