package com.example.csvccdshustbe.repository.modules;

import com.example.csvccdshustbe.entity.Modules;

import java.util.List;

public interface ModulesRepositoryCustom {

    List<Modules> findAllModulesByIdAssetCategoryAndStatus(Integer idAssetCategory, Integer status);
}
