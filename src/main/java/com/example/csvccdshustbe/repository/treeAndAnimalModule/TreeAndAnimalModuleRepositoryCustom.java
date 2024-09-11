package com.example.csvccdshustbe.repository.treeAndAnimalModule;

import com.example.csvccdshustbe.entity.AnimalTreeModule;

import java.util.Optional;

public interface TreeAndAnimalModuleRepositoryCustom {

    Optional<AnimalTreeModule> findAnimalTreeModulesById(Integer idAnimalTree);

}
