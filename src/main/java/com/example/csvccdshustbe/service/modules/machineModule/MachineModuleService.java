package com.example.csvccdshustbe.service.modules.machineModule;

import com.example.csvccdshustbe.entity.MachineModule;

import java.util.Map;

public interface MachineModuleService {

    MachineModule save(MachineModule machineModule);

    void validateDataCreate(Map<String, Object> dataModule);

    Map<String,Object> findMachineModuleDetailsByIdMachineModule(Integer machineModule) throws IllegalAccessException;

    void deleteMachineModuleById(Integer idInstance);

    MachineModule findMachineModuleByIdMachineModule(Integer idInstance);
}
