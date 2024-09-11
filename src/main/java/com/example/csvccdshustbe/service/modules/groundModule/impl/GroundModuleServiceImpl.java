package com.example.csvccdshustbe.service.modules.groundModule.impl;

import com.example.csvccdshustbe.entity.GroundModule;
import com.example.csvccdshustbe.repository.groundModule.GroundModuleRepository;
import com.example.csvccdshustbe.service.modules.groundModule.GroundModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class GroundModuleServiceImpl implements GroundModuleService {

    @Autowired
    GroundModuleRepository groundModuleRepository;

    @Override
    public GroundModule saveGroundModule(GroundModule module) {
        return groundModuleRepository.save(module);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findGroundModuleByIdGroundModule(Integer groundModule) {
        Optional<GroundModule> module = groundModuleRepository.findGroundModuleByIdGroundModule(groundModule);
        if (!module.isPresent()){
            throw new NotFoundException("Don't exits grounds modules!");
        }
        return null;
    }
}
