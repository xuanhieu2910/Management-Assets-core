package com.example.csvccdshustbe.service.medicineType;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineType.CreateMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.UpdateMedicineTypeRequest;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeResponse;
import org.springframework.data.domain.Page;

public interface MedicineTypeService {

    Page<FindAllMedicineTypeResponse> findAllMedicineTypeResponse(FindAllMedicineTypeRequest request);

    void createMedicineType(CreateMedicineTypeRequest request) throws ValidateFiledException;

    void updateMedicineType(UpdateMedicineTypeRequest request) throws ValidateFiledException;

    void deleteMedicineTypeByIdMedicineType(Integer idMedicineType);
}
