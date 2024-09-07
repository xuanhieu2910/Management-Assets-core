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
        medicineModule.setSparePartsAttack(ValueUtil.getStringByObject(mapModuleCreate.get("sparePartsAttack")));
        return medicineModule;
    }
}
