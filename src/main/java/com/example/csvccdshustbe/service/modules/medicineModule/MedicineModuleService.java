package com.example.csvccdshustbe.service.modules.medicineModule;

import com.example.csvccdshustbe.dto.modules.medicineModules.MedicineModuleDetailsDto;
import com.example.csvccdshustbe.entity.MedicineModule;

import java.util.Map;

public interface MedicineModuleService {

    MedicineModule save(MedicineModule medicineModule);

    void validateDataCreate(Map<String, Object> dataModule);

    MedicineModuleDetailsDto findMedicineModuleDetailsByIdMedicine(Integer idMedicine) throws IllegalAccessException;


    void deleteMedicineModuleById(Integer idInstance);

    MedicineModule findMedicineModuleByIdMedicine(Integer idInstance);

}
