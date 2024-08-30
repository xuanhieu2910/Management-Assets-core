package com.example.csvccdshustbe.repository.medicine;

import com.example.csvccdshustbe.dto.medicine.FindAllMedicineTypeDto;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineTypeRepositoryCustom {

    Page<FindAllMedicineTypeDto> findAllMedicineTypeVisible(FindAllMedicineTypeRequest request, Pageable pageable);
}
