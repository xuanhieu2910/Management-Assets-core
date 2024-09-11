package com.example.csvccdshustbe.repository.machineModule;

import com.example.csvccdshustbe.dto.modules.machineModules.MachineModuleDetailsDto;

import java.util.Optional;

public interface MachineModuleRepositoryCustom {

    Optional<MachineModuleDetailsDto> findMachineModuleDetailsDtoById(Integer idMachineModule);
}
