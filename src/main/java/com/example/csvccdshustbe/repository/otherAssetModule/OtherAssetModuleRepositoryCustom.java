package com.example.csvccdshustbe.repository.otherAssetModule;

import com.example.csvccdshustbe.dto.modules.otherAssetModules.OtherAssetModulesDetailsDto;
import com.example.csvccdshustbe.entity.OtherAssetModule;

import java.util.Optional;

public interface OtherAssetModuleRepositoryCustom {

    Optional<OtherAssetModulesDetailsDto> findOtherAssetModuleDetailsDtoByIdOtherAssetModule(Integer idOtherAssetModule);

    void deleteOtherAssetModuleByIdOtherAsset(Integer idInstance);
}
