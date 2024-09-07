package com.example.csvccdshustbe.service.modules;

import com.example.csvccdshustbe.response.modules.FindAllModulesResponse;

import java.util.List;

public interface ModulesService {


    List<FindAllModulesResponse> findAllModulesByIdAssetCategory(Integer idAssetCategory);
}
