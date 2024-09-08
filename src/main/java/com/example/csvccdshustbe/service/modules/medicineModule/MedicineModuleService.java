package com.example.csvccdshustbe.service.modules.medicineModule;

import com.example.csvccdshustbe.entity.MedicineModule;

import java.util.Map;

public interface MedicineModuleService {

    MedicineModule save(MedicineModule medicineModule);

    void validateDataCreate(Map<String, Object> dataModule);
}
