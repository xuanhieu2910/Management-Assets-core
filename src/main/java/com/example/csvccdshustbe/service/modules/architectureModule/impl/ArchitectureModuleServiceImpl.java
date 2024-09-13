package com.example.csvccdshustbe.service.modules.architectureModule.impl;

import com.example.csvccdshustbe.dto.modules.architectureModules.ArchitectureModulesDetailsDto;
import com.example.csvccdshustbe.entity.ArchitectureModule;
import com.example.csvccdshustbe.repository.architectureModule.ArchitectureModuleRepository;
import com.example.csvccdshustbe.service.modules.architectureModule.ArchitectureModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
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
    public Map<String,Object> findArchitectureModuleDetailsByIdArchitectureModule(Integer idArchitectureModule)
            throws IllegalAccessException {
        Optional<ArchitectureModulesDetailsDto> module = architectureModuleRepository.
                findArchitectureModuleDetailsDtoByIdArchitectureModule(idArchitectureModule);
        if (!module.isPresent()){
            throw new NotFoundException("Don't exits architecture module!");
        }
        return ValueUtil.convertObjectToMap(module.get());
    }

    @Override
    public void deleteArchitectureById(Integer idInstance) {
        architectureModuleRepository.deleteArchitectureModuleByIdArchitecture(idInstance);
    }

    @Override
    public ArchitectureModule findArchitectureModuleByIdArchitectureModule(Integer idInstance) {
        Optional<ArchitectureModule> architectureModule = architectureModuleRepository.
                findArchitectureModuleByIdArchitectureModule(idInstance);
        if (architectureModule.isEmpty()){
            throw new NotFoundException("Don't exits architecture modules by id!");
        }
        return architectureModule.get();
    }
}
