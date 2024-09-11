package com.example.csvccdshustbe.service.modules.otherAssetModule;

import com.example.csvccdshustbe.entity.OtherAssetModule;

import java.util.Map;

public interface OtherAssetModuleService {

    OtherAssetModule save(OtherAssetModule otherAssetModule);

    void validateDataCreate(Map<String, Object> dataModule);

    Map<String,Object> findOtherAssetModuleByIdOtherAssetModule(Integer idOtherAssetModule) throws IllegalAccessException;
}
