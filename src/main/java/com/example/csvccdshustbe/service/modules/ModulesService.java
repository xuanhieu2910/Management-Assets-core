package com.example.csvccdshustbe.service.modules;

import com.example.csvccdshustbe.entity.Modules;
import com.example.csvccdshustbe.response.modules.FindAllModulesResponse;

import java.util.List;

public interface ModulesService {


    List<FindAllModulesResponse> findAllModulesByIdAssetCategory(Integer idAssetCategory);

    Modules findModulesByTypeModules(String stringByObject);
}
