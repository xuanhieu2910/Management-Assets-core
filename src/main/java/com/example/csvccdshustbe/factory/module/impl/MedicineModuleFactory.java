package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.entity.MedicineModule;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class MedicineModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        MedicineModule medicineModule = new MedicineModule();
        medicineModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        medicineModule.setIdMedicineType(ValueUtil.getIntegerByObject(mapModuleCreate.get("idMedicineType")));
        medicineModule.setIdMedicineGroup(ValueUtil.getIntegerByObject(mapModuleCreate.get("idMedicineGroup")));
        medicineModule.setPublishDate(ValueUtil.getStringByObject(mapModuleCreate.get("publishDate")));
        medicineModule.setExpiryDate(ValueUtil.getStringByObject(mapModuleCreate.get("expiryDate")));
        medicineModule.setCirculationNumber(ValueUtil.getStringByObject(mapModuleCreate.get("circulationNumber")));
        medicineModule.setNumberBatchOfGoods(ValueUtil.getStringByObject(mapModuleCreate.get("numberBatchOfGoods")));
        medicineModule.setOwnNameCirculationNumber(ValueUtil.getStringByObject(mapModuleCreate.get("ownNameCirculationNumber")));
        medicineModule.setOwnAddressCirculationNumber(ValueUtil.getStringByObject(mapModuleCreate.get("ownAddressCirculationNumber")));
        return medicineModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        MedicineModule medicineModule = (MedicineModule) iModules;
        medicineModule.setIdMedicineType(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idMedicineType")));
        medicineModule.setIdMedicineGroup(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idMedicineGroup")));
        medicineModule.setPublishDate(ValueUtil.getStringByObject(mapModuleUpdate.get("publishDate")));
        medicineModule.setExpiryDate(ValueUtil.getStringByObject(mapModuleUpdate.get("expiryDate")));
        medicineModule.setCirculationNumber(ValueUtil.getStringByObject(mapModuleUpdate.get("circulationNumber")));
        medicineModule.setNumberBatchOfGoods(ValueUtil.getStringByObject(mapModuleUpdate.get("numberBatchOfGoods")));
        medicineModule.setOwnNameCirculationNumber(ValueUtil.getStringByObject(mapModuleUpdate.get("ownNameCirculationNumber")));
        medicineModule.setOwnAddressCirculationNumber(ValueUtil.getStringByObject(mapModuleUpdate.get("ownAddressCirculationNumber")));
        return medicineModule;
    }
}
