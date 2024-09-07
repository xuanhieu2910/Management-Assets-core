package com.example.csvccdshustbe.service.modules.impl;

import com.example.csvccdshustbe.entity.Modules;
import com.example.csvccdshustbe.repository.modules.ModulesRepository;
import com.example.csvccdshustbe.response.modules.FindAllModulesResponse;
import com.example.csvccdshustbe.service.modules.ModulesService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModulesServiceImpl implements ModulesService {


    @Autowired
    ModulesRepository modulesRepository;

    @Override
    public List<FindAllModulesResponse> findAllModulesByIdAssetCategory(Integer idAssetCategory) {
        List<Modules> findAllModules = modulesRepository.findAllModulesByIdAssetCategoryAndStatus(idAssetCategory, Constants.MODULES_VISIBLE);
        return convertToFindAllByIdAssetCategory(findAllModules);
    }

    private List<FindAllModulesResponse> convertToFindAllByIdAssetCategory(List<Modules> findAllModules) {
        List<FindAllModulesResponse> responses = new ArrayList<>();
        for (Modules modules : findAllModules){
            FindAllModulesResponse response = new FindAllModulesResponse();
            response.setIdModules(modules.getIdModule());
            response.setName(modules.getName());
            response.setCode(modules.getCode());
            response.setIdAssetCategory(modules.getIdAssetCategory());
            response.setHardCode(modules.getHardCode());
            responses.add(response);
        }
        return responses;
    }
}
