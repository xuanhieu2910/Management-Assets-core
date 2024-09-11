package com.example.csvccdshustbe.service.modules.treeAndAnimalModule;

import com.example.csvccdshustbe.entity.AnimalTreeModule;

import java.util.Map;

public interface TreeAndAnimalModuleService {

    AnimalTreeModule save(AnimalTreeModule animalTreeModule);

    void validateDataCreate(Map<String, Object> dataModule);

    Map<String,Object> findAnimalTreeModuleByIdAnimalTree(Integer idAnimalTree) throws IllegalAccessException;
}
