package com.example.csvccdshustbe.service.modules.treeAndAnimalModule.impl;

import com.example.csvccdshustbe.dto.modules.treeAndAnimalModules.TreeAndAnimalModulesDetailsDto;
import com.example.csvccdshustbe.entity.AnimalTreeModule;
import com.example.csvccdshustbe.repository.treeAndAnimalModule.TreeAndAnimalModuleRepository;
import com.example.csvccdshustbe.service.modules.treeAndAnimalModule.TreeAndAnimalModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class TreeAndAnimalModuleServiceImpl implements TreeAndAnimalModuleService {

    @Autowired
    TreeAndAnimalModuleRepository treeAndAnimalModuleRepository;

    @Override
    public AnimalTreeModule save(AnimalTreeModule animalTreeModule) {
        return treeAndAnimalModuleRepository.save(animalTreeModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findAnimalTreeModuleByIdAnimalTree(Integer idAnimalTree) throws IllegalAccessException {
        Optional<TreeAndAnimalModulesDetailsDto> animalTreeModule = treeAndAnimalModuleRepository.findAnimalTreeModulesDetailsDtoById(idAnimalTree);
        if (!animalTreeModule.isPresent()) {
            throw new NotFoundException("Don't exits animal tree modules");
        }
        return ValueUtil.convertObjectToMap(animalTreeModule.get());
    }
}
