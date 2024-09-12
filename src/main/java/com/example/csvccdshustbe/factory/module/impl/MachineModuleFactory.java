package com.example.csvccdshustbe.factory.module.impl;

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
        return machineModule;
    }
}
