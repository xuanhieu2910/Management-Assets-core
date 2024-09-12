package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.entity.ArchitectureModule;
import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class ArchitectureModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        ArchitectureModule architectureModule = new ArchitectureModule();
        architectureModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        architectureModule.setIdInstance(ValueUtil.getIntegerByObject(mapModuleCreate.get("idInstance")));
        architectureModule.setLength(ValueUtil.getDoubleByObject(mapModuleCreate.get("length")));
        architectureModule.setAcreage(ValueUtil.getDoubleByObject(mapModuleCreate.get("acreage")));
        architectureModule.setVolume(ValueUtil.getDoubleByObject(mapModuleCreate.get("volume")));
        architectureModule.setPublishDate(ValueUtil.getStringByObject(mapModuleCreate.get("publishDate")));
        architectureModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleCreate.get("idCountryProducer")));
        return architectureModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        ArchitectureModule architectureModule  = (ArchitectureModule) iModules;
        architectureModule.setIdInstance(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idInstance")));
        architectureModule.setLength(ValueUtil.getDoubleByObject(mapModuleUpdate.get("length")));
        architectureModule.setAcreage(ValueUtil.getDoubleByObject(mapModuleUpdate.get("acreage")));
        architectureModule.setVolume(ValueUtil.getDoubleByObject(mapModuleUpdate.get("volume")));
        architectureModule.setPublishDate(ValueUtil.getStringByObject(mapModuleUpdate.get("publishDate")));
        architectureModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idCountryProducer")));
        return architectureModule;
    }
}
