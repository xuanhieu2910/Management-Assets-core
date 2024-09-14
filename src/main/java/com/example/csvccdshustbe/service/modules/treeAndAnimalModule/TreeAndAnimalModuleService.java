package com.example.csvccdshustbe.service.modules.treeAndAnimalModule;

import com.example.csvccdshustbe.dto.modules.treeAndAnimalModules.TreeAndAnimalModulesDetailsDto;
import com.example.csvccdshustbe.entity.AnimalTreeModule;

import java.util.Map;

public interface TreeAndAnimalModuleService {

    AnimalTreeModule save(AnimalTreeModule animalTreeModule);

    void validateDataCreate(Map<String, Object> dataModule);

    TreeAndAnimalModulesDetailsDto findAnimalTreeModuleDetailsByIdAnimalTree(Integer idAnimalTree) throws IllegalAccessException;

    void deleteTreeAndAnimalById(Integer idInstance);

    AnimalTreeModule findAnimalTreeModuleByIdAnimalTree(Integer idInstance);
}
