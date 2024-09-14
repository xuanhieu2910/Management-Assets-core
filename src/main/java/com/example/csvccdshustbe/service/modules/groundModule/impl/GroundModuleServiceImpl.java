package com.example.csvccdshustbe.service.modules.groundModule.impl;

import com.example.csvccdshustbe.dto.modules.groundModules.GroundModulesDetailsDto;
import com.example.csvccdshustbe.entity.GroundModule;
import com.example.csvccdshustbe.repository.groundModule.GroundModuleRepository;
import com.example.csvccdshustbe.service.modules.groundModule.GroundModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
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
    public GroundModule save(GroundModule module) {
        return groundModuleRepository.save(module);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public GroundModulesDetailsDto findGroundModuleDetailsByIdGroundModule(Integer groundModule) throws IllegalAccessException {
        Optional<GroundModulesDetailsDto> module = groundModuleRepository.findGroundModuleDetailsDtoByIdGroundModule(groundModule);
        if (module.isEmpty()){
            throw new NotFoundException("Don't exits grounds modules!");
        }
        return module.get();
    }

    @Override
    public void deleteGroundModuleById(Integer idInstance) {
        groundModuleRepository.deleteGroundModuleByIdGroundModule(idInstance);
    }

    @Override
    public GroundModule findGroundModuleByIdGroundModule(Integer idInstance) {
        Optional<GroundModule> module = groundModuleRepository.findGroundModuleByIdGroundModule(idInstance);
        if (module.isEmpty()){
            throw new NotFoundException("Don't exits ground module by id!");
        }
        return module.get();
    }
}
