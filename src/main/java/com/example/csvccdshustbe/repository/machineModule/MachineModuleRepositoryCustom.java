package com.example.csvccdshustbe.repository.machineModule;

import com.example.csvccdshustbe.entity.MachineModule;

import java.util.Optional;

public interface MachineModuleRepositoryCustom {

    Optional<MachineModule> findMachineModuleById(Integer idMachineModule);
}
