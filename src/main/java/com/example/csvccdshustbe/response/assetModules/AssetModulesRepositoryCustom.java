package com.example.csvccdshustbe.response.assetModules;

import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.entity.AssetModules;

import java.util.List;

public interface AssetModulesRepositoryCustom {

    List<AssetModules> findAllAssetModulesByIdAsset(Integer idAsset);

    List<BluePrintAssetModulesDto> findBluePrintAssetModulesDtoByIdAsset(Integer idAsset);

    void deleteAssetModulesByIdInstanceAndIdModule(Integer idInstance, Integer idModule);

}
