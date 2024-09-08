package com.example.csvccdshustbe.repository.modules;

import com.example.csvccdshustbe.entity.Modules;

import java.util.List;
import java.util.Optional;

public interface ModulesRepositoryCustom {

    List<Modules> findAllModulesByIdAssetCategoryAndStatus(Integer idAssetCategory, Integer status);
    Optional<Modules> findModulesByTypeModulesAndStatus(String typeModules, Integer status);
}
