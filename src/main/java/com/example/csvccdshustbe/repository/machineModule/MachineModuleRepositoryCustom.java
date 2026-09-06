package com.example.csvccdshustbe.repository.machineModule;

import com.example.csvccdshustbe.dto.modules.machineModules.MachineModuleDetailsDto;
import com.example.csvccdshustbe.entity.MachineModule;

import java.util.Optional;

public interface MachineModuleRepositoryCustom {

    Optional<MachineModuleDetailsDto> findMachineModuleDetailsDtoById(Integer idMachineModule);

    void deleteMachineModuleByIdMachineModule(Integer idMachineModule);

    Optional<MachineModule> findMachineModuleByIdMachineModule(Integer machineModule);
}
