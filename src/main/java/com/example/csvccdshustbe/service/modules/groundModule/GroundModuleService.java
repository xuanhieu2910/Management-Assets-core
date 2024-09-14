package com.example.csvccdshustbe.service.modules.groundModule;

import com.example.csvccdshustbe.dto.modules.groundModules.GroundModulesDetailsDto;
import com.example.csvccdshustbe.entity.GroundModule;

import java.util.Map;

public interface GroundModuleService {
    GroundModule save(GroundModule module);

    void validateDataCreate(Map<String, Object> dataModule);

    GroundModulesDetailsDto findGroundModuleDetailsByIdGroundModule(Integer groundModule) throws IllegalAccessException;

    void deleteGroundModuleById(Integer idInstance);

    GroundModule findGroundModuleByIdGroundModule(Integer idInstance);
}
