package com.example.csvccdshustbe.repository.groundModule;

import com.example.csvccdshustbe.dto.modules.groundModules.GroundModulesDetailsDto;
import com.example.csvccdshustbe.entity.GroundModule;

import java.util.Optional;

public interface GroundModuleRepositoryCustom {

    Optional<GroundModulesDetailsDto> findGroundModuleDetailsDtoByIdGroundModule(Integer idGroundModule);

    void deleteGroundModuleByIdGroundModule(Integer idInstance);
}
