package com.example.csvccdshustbe.repository.medicineModule;

import com.example.csvccdshustbe.entity.MedicineModule;

import java.util.Optional;

public interface MedicineModuleRepositoryCustom {

    Optional<MedicineModule> findMedicineModuleById(Integer idMedicineModule);
}
