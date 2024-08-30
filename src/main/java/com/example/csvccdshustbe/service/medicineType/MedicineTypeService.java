package com.example.csvccdshustbe.service.medicineType;

import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeResponse;
import org.springframework.data.domain.Page;

public interface MedicineTypeService {

    Page<FindAllMedicineTypeResponse> findAllMedicineTypeResponse(FindAllMedicineTypeRequest request);
}
