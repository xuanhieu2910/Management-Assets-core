package com.example.csvccdshustbe.service.modules.machineModule.impl;

import com.example.csvccdshustbe.entity.MachineModule;
import com.example.csvccdshustbe.repository.machineModule.MachineModuleRepository;
import com.example.csvccdshustbe.service.modules.machineModule.MachineModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
}
