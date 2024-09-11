package com.example.csvccdshustbe.service.modules.architectureModule.impl;

import com.example.csvccdshustbe.entity.ArchitectureModule;
import com.example.csvccdshustbe.repository.architectureModule.ArchitectureModuleRepository;
import com.example.csvccdshustbe.service.modules.architectureModule.ArchitectureModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class ArchitectureModuleServiceImpl implements ArchitectureModuleService {

    @Autowired
    ArchitectureModuleRepository architectureModuleRepository;

    @Override
    public ArchitectureModule save(ArchitectureModule architectureModule) {
        return architectureModuleRepository.save(architectureModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String,Object> findArchitectureModuleByIdArchitectureModule(Integer idArchitectureModule) {
        Optional<ArchitectureModule> module = architectureModuleRepository.findArchitectureModuleByIdArchitectureModule(idArchitectureModule);
        if (!module.isPresent()){
            throw new NotFoundException("Don't exits architecture module!");
        }
        return null;
    }
}
