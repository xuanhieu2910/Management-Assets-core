package com.example.csvccdshustbe.service.modules.groundModule.impl;

import com.example.csvccdshustbe.entity.GroundModule;
import com.example.csvccdshustbe.repository.groundModule.GroundModuleRepository;
import com.example.csvccdshustbe.service.modules.groundModule.GroundModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroundModuleServiceImpl implements GroundModuleService {

    @Autowired
    GroundModuleRepository groundModuleRepository;

    @Override
    public GroundModule saveGroundModule(GroundModule module) {
        return groundModuleRepository.save(module);
    }
}
