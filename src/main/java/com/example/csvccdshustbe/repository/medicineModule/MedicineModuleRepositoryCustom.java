package com.example.csvccdshustbe.repository.medicineModule;

import com.example.csvccdshustbe.dto.modules.medicineModules.MedicineModuleDetailsDto;
import com.example.csvccdshustbe.entity.MedicineModule;

import java.util.Optional;

public interface MedicineModuleRepositoryCustom {

    Optional<MedicineModuleDetailsDto> findMedicineModuleDetailsDtoById(Integer idMedicineModule);

    void deleteMedicineModuleById(Integer idMedicineModule);

    Optional<MedicineModule> findMedicineModuleById(Integer idInstance);
}
