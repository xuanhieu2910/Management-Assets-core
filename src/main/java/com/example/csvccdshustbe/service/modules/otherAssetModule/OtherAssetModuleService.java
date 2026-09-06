package com.example.csvccdshustbe.service.modules.otherAssetModule;

import com.example.csvccdshustbe.dto.modules.otherAssetModules.OtherAssetModulesDetailsDto;
import com.example.csvccdshustbe.entity.OtherAssetModule;

import java.util.Map;

public interface OtherAssetModuleService {

    OtherAssetModule save(OtherAssetModule otherAssetModule);

    void validateDataCreate(Map<String, Object> dataModule);

    OtherAssetModulesDetailsDto findOtherAssetModuleDetailsByIdOtherAssetModule(Integer idOtherAssetModule) throws IllegalAccessException;

    void deleteOtherAssetById(Integer idInstance);

    OtherAssetModule findOtherAssetModuleByIdOtherAssetModule(Integer idInstance);
}
