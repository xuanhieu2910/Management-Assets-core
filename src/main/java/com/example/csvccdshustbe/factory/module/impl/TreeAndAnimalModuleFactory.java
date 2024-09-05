package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.entity.AnimalTreeModule;
import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.entity.Modules;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class TreeAndAnimalModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        AnimalTreeModule animalTreeModule = new AnimalTreeModule();
        animalTreeModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        animalTreeModule.setPublishDate(ValueUtil.getStringByObject(mapModuleCreate.get("publishDate")));
        animalTreeModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleCreate.get("idTypeUse")));
        return animalTreeModule;
    }
}
