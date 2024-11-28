package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
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
        otherAssetModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleCreate.get("sparePartsAttack")));
        return otherAssetModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        OtherAssetModule otherAssetModule = (OtherAssetModule) iModules;
        otherAssetModule.setLabel(ValueUtil.getStringByObject(mapModuleUpdate.get("label")));
        otherAssetModule.setModel(ValueUtil.getStringByObject(mapModuleUpdate.get("model")));
        otherAssetModule.setSerial(ValueUtil.getStringByObject(mapModuleUpdate.get("serial")));
        otherAssetModule.setPublishDate(ValueUtil.getStringByObject(mapModuleUpdate.get("publishDate")));
        otherAssetModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idCountryProducer")));
        otherAssetModule.setIdUser(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idUser")));
        otherAssetModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idTypeUse")));
        otherAssetModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleUpdate.get("sparePartsAttack")));
        return otherAssetModule;
    }

    @Override
    public IModules copyModule(AssetModulesDto assetModulesDto, Integer idAsset) {
        OtherAssetModule otherAssetModuleRoot = (OtherAssetModule) assetModulesDto.getDataDetails();
        OtherAssetModule otherAssetModule = new OtherAssetModule();
        otherAssetModule.setIdAsset(idAsset);
        otherAssetModule.setLabel(otherAssetModuleRoot.getLabel());
        otherAssetModule.setModel(otherAssetModuleRoot.getModel());
        otherAssetModule.setSerial(otherAssetModuleRoot.getSerial());
        otherAssetModule.setPublishDate(otherAssetModuleRoot.getPublishDate());
        otherAssetModule.setIdCountryProducer(otherAssetModuleRoot.getIdCountryProducer());
        otherAssetModule.setIdUser(otherAssetModuleRoot.getIdUser());
        otherAssetModule.setIdTypeUse(otherAssetModuleRoot.getIdTypeUse());
        otherAssetModule.setSparePartsAttack(otherAssetModuleRoot.getSparePartsAttack());
        return otherAssetModule;
    }
}
