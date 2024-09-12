package com.example.csvccdshustbe.repository.treeAndAnimalModule;

import com.example.csvccdshustbe.dto.modules.treeAndAnimalModules.TreeAndAnimalModulesDetailsDto;
import com.example.csvccdshustbe.entity.AnimalTreeModule;

import java.util.Optional;

public interface TreeAndAnimalModuleRepositoryCustom {

    Optional<TreeAndAnimalModulesDetailsDto> findAnimalTreeModulesDetailsDtoById(Integer idAnimalTree);

    void deleteTreeAndAnimalModuleById(Integer idInstance);
}
