package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.entity.AnimalTreeModule;
import com.example.csvccdshustbe.entity.IModules;
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
        animalTreeModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleCreate.get("idCountryProducer")));
        animalTreeModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleCreate.get("sparePartsAttack")));
        return animalTreeModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        AnimalTreeModule animalTreeModule = (AnimalTreeModule) iModules;
        animalTreeModule.setPublishDate(ValueUtil.getStringByObject(mapModuleUpdate.get("publishDate")));
        animalTreeModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idTypeUse")));
        animalTreeModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idCountryProducer")));
        animalTreeModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleUpdate.get("sparePartsAttack")));
        return animalTreeModule;
    }

    @Override
    public IModules copyModule(AssetModulesDto assetModulesDto, Integer idAsset) {
        AnimalTreeModule animalTreeModuleRoot = (AnimalTreeModule) assetModulesDto.getDataDetails();
        AnimalTreeModule animalTreeModule = new AnimalTreeModule();
        animalTreeModule.setIdAsset(idAsset);
        animalTreeModule.setPublishDate(animalTreeModuleRoot.getPublishDate());
        animalTreeModule.setIdTypeUse(animalTreeModuleRoot.getIdTypeUse());
        animalTreeModule.setIdCountryProducer(animalTreeModuleRoot.getIdCountryProducer());
        animalTreeModule.setSparePartsAttack(animalTreeModuleRoot.getSparePartsAttack());
        return animalTreeModule;
    }
}
