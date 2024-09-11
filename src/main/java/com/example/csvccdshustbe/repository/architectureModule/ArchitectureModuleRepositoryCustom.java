package com.example.csvccdshustbe.repository.architectureModule;

import com.example.csvccdshustbe.entity.ArchitectureModule;

import java.util.Optional;

public interface ArchitectureModuleRepositoryCustom {

    Optional<ArchitectureModule> findArchitectureModuleByIdArchitectureModule(Integer idArchitectureModule);
}
