package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.entity.MachineModule;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class MachineModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        MachineModule machineModule = new MachineModule();
        machineModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        machineModule.setLabelMachine(ValueUtil.getStringByObject(mapModuleCreate.get("labelMachine")));
        machineModule.setModel(ValueUtil.getStringByObject(mapModuleCreate.get("model")));
        machineModule.setSerial(ValueUtil.getStringByObject(mapModuleCreate.get("serial")));
        machineModule.setPublishDate(ValueUtil.getStringByObject(mapModuleCreate.get("publishDate")));
        machineModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleCreate.get("idCountryProducer")));
        machineModule.setIdUser(ValueUtil.getIntegerByObject(mapModuleCreate.get("idUser")));
        machineModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleCreate.get("idTypeUse")));
        machineModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleCreate.get("sparePartsAttack")));
        return machineModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        MachineModule machineModule = (MachineModule) iModules;
        machineModule.setLabelMachine(ValueUtil.getStringByObject(mapModuleUpdate.get("labelMachine")));
        machineModule.setModel(ValueUtil.getStringByObject(mapModuleUpdate.get("model")));
        machineModule.setSerial(ValueUtil.getStringByObject(mapModuleUpdate.get("serial")));
        machineModule.setPublishDate(ValueUtil.getStringByObject(mapModuleUpdate.get("publishDate")));
        machineModule.setIdCountryProducer(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idCountryProducer")));
        machineModule.setIdUser(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idUser")));
        machineModule.setIdTypeUse(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idTypeUse")));
        machineModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleUpdate.get("sparePartsAttack")));
        return machineModule;
    }

    @Override
    public IModules copyModule(AssetModulesDto assetModulesDto, Integer idAsset) {
        MachineModule machineModuleRoot = (MachineModule) assetModulesDto.getDataDetails();
        MachineModule machineModule = new MachineModule();
        machineModule.setIdAsset(idAsset);
        machineModule.setLabelMachine(machineModuleRoot.getLabelMachine());
        machineModule.setModel(machineModuleRoot.getModel());
        machineModule.setSerial(machineModuleRoot.getSerial());
        machineModule.setPublishDate(machineModuleRoot.getPublishDate());
        machineModule.setIdCountryProducer(machineModuleRoot.getIdCountryProducer());
        machineModule.setIdUser(machineModuleRoot.getIdUser());
        machineModule.setIdTypeUse(machineModuleRoot.getIdTypeUse());
        machineModule.setSparePartsAttack(machineModuleRoot.getSparePartsAttack());
        return machineModule;
    }
}
