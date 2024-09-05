package com.example.csvccdshustbe.service.modules.treeAndAnimalModule.impl;

import com.example.csvccdshustbe.entity.AnimalTreeModule;
import com.example.csvccdshustbe.repository.treeAndAnimalModule.TreeAndAnimalModuleRepository;
import com.example.csvccdshustbe.service.modules.treeAndAnimalModule.TreeAndAnimalModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeAndAnimalModuleServiceImpl implements TreeAndAnimalModuleService {

    @Autowired
    TreeAndAnimalModuleRepository treeAndAnimalModuleRepository;

    @Override
    public AnimalTreeModule save(AnimalTreeModule animalTreeModule) {
        return treeAndAnimalModuleRepository.save(animalTreeModule);
    }
}
