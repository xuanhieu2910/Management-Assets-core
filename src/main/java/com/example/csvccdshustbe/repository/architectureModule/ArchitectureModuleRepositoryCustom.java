package com.example.csvccdshustbe.repository.architectureModule;

import com.example.csvccdshustbe.dto.modules.architectureModules.ArchitectureModulesDetailsDto;
import com.example.csvccdshustbe.entity.ArchitectureModule;

import java.util.Optional;

public interface ArchitectureModuleRepositoryCustom {

    Optional<ArchitectureModulesDetailsDto> findArchitectureModuleDetailsDtoByIdArchitectureModule(Integer idArchitectureModule);

    void deleteArchitectureModuleByIdArchitecture(Integer idInstance);

    Optional<ArchitectureModule> findArchitectureModuleByIdArchitectureModule(Integer idInstance);
}
