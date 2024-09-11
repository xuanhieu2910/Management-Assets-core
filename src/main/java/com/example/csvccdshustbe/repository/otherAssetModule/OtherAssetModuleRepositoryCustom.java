package com.example.csvccdshustbe.repository.otherAssetModule;

import com.example.csvccdshustbe.entity.OtherAssetModule;

import java.util.Optional;

public interface OtherAssetModuleRepositoryCustom {

    Optional<OtherAssetModule> findOtherAssetModuleByIdOtherAssetModule(Integer idOtherAssetModule);
}
