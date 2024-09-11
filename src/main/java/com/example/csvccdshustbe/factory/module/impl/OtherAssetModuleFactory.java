package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.entity.Modules;
import com.example.csvccdshustbe.entity.OtherAssetModule;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class OtherAssetModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        OtherAssetModule otherAssetModule = new OtherAssetModule();
        otherAssetModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        otherAssetModule.setLabel(ValueUtil.getStringByObject(mapModuleCreate.get("label")));
        otherAssetModule.setModel(ValueUtil.getStringByObject(mapModuleCreate.get("model")));
        otherAssetModule.setSerial(ValueUtil.getStringByObject(mapModuleCreate.get("serial")));
        otherAssetModule.setPublishDate(ValueUtil.getStringByObject(mapModuleCreate.get("publishDate")));
        otherAssetModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleCreate.get("idCountryProducer")));
        otherAssetModule.setIdUser(ValueUtil.getIntegerByObject(mapModuleCreate.get("idUser")));
        otherAssetModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleCreate.get("idTypeUse")));
        return otherAssetModule;
    }
}
