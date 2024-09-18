package com.example.csvccdshustbe.service.medicineType;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineType.CreateMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeRequest;
import com.example.csvccdshustbe.request.medicineType.FindAllMedicineTypeVisibleRequest;
import com.example.csvccdshustbe.request.medicineType.UpdateMedicineTypeRequest;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeResponse;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeVisibleResponse;
import org.springframework.data.domain.Page;

public interface MedicineTypeService {

    Page<FindAllMedicineTypeVisibleResponse> findAllMedicineTypeVisibleResponse(FindAllMedicineTypeVisibleRequest request);
    Page<FindAllMedicineTypeResponse> findAllMedicineTypeResponse(FindAllMedicineTypeRequest request);

    void createMedicineType(CreateMedicineTypeRequest request) throws ValidateFiledException;

    void updateMedicineType(UpdateMedicineTypeRequest request) throws ValidateFiledException;

    void deleteMedicineTypeByIdMedicineType(Integer idMedicineType);
}
