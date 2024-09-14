package com.example.csvccdshustbe.service.modules.architectureModule;

import com.example.csvccdshustbe.dto.modules.architectureModules.ArchitectureModulesDetailsDto;
import com.example.csvccdshustbe.entity.ArchitectureModule;

import java.util.Map;

public interface ArchitectureModuleService {

    ArchitectureModule save(ArchitectureModule architectureModule);

    void validateDataCreate(Map<String, Object> dataModule);

    ArchitectureModulesDetailsDto findArchitectureModuleDetailsByIdArchitectureModule(Integer idArchitectureModule) throws IllegalAccessException;

    void deleteArchitectureById(Integer idInstance);

    ArchitectureModule findArchitectureModuleByIdArchitectureModule(Integer idInstance);
}
