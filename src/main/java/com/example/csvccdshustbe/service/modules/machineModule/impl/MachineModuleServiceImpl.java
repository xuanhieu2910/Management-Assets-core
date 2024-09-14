package com.example.csvccdshustbe.service.modules.machineModule.impl;

import com.example.csvccdshustbe.dto.modules.machineModules.MachineModuleDetailsDto;
import com.example.csvccdshustbe.entity.MachineModule;
import com.example.csvccdshustbe.repository.machineModule.MachineModuleRepository;
import com.example.csvccdshustbe.service.modules.machineModule.MachineModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class MachineModuleServiceImpl implements MachineModuleService {

    @Autowired
    MachineModuleRepository machineModuleRepository;

    @Override
    public MachineModule save(MachineModule machineModule) {
        return machineModuleRepository.save(machineModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public MachineModule findMachineModuleByIdMachineModule(Integer machineModule) {
        Optional<MachineModule> optionalMachineModule = machineModuleRepository.findMachineModuleByIdMachineModule(machineModule);
        if (optionalMachineModule.isEmpty()){
            throw new NotFoundException("Don't exits machine module by id!");
        }
        return optionalMachineModule.get();
    }

    @Override
    public MachineModuleDetailsDto findMachineModuleDetailsByIdMachineModule(Integer machineModule) throws IllegalAccessException {
        Optional<MachineModuleDetailsDto> module = machineModuleRepository.findMachineModuleDetailsDtoById(machineModule);
        if (module.isEmpty()) {
            throw new NotFoundException("Don't exits machine modules");
        }
        return module.get();
    }

    @Override
    public void deleteMachineModuleById(Integer idInstance) {
        machineModuleRepository.deleteMachineModuleByIdMachineModule(idInstance);
    }
}
