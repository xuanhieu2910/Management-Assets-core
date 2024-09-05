package com.example.csvccdshustbe.factory.module;

import com.example.csvccdshustbe.entity.IModules;

import java.util.Map;

public interface ModuleFactory {

    IModules createModule(Map<String, Object> mapModuleCreate);

}
