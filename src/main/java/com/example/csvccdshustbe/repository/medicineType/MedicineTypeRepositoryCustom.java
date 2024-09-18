package com.example.csvccdshustbe.repository.medicineType;

import com.example.csvccdshustbe.dto.modules.medicineModules.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.entity.MedicineType;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MedicineTypeRepositoryCustom {

    Page<FindAllMedicineTypeDto> findAllMedicineTypeVisible(FindAllMedicineTypeVisibleRequest request, Pageable pageable);
    Page<FindAllMedicineTypeDto> findAllMedicineType(FindAllMedicineTypeRequest request, Pageable pageable);

    Optional<MedicineType> findMedicineTypeByName(String name);

    Optional<MedicineType> findMedicineTypeByIdParent(Integer idParent);

    Optional<MedicineType> findMedicineTypeById(Integer idMedicineType);

    boolean checkExitsMedicineTypeByNameOrCodeOrShortName(String name, String code,String shortName);

}
