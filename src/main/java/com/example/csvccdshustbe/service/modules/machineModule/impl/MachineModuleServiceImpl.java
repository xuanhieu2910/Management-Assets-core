package com.example.csvccdshustbe.service.modules.machineModule.impl;

import com.example.csvccdshustbe.dto.modules.machineModules.MachineModuleDetailsDto;
import com.example.csvccdshustbe.entity.MachineModule;
import com.example.csvccdshustbe.repository.machineModule.MachineModuleRepository;
import com.example.csvccdshustbe.service.modules.machineModule.MachineModuleService;
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
    public Map<String, Object> findMachineModuleByIdMachineModule(Integer machineModule) {
        Optional<MachineModuleDetailsDto> module = machineModuleRepository.findMachineModuleDetailsDtoById(machineModule);
        if (!module.isPresent()) {
            throw new NotFoundException("Don't exits machine modules");
        }
        return null;
    }
}
