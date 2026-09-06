package com.example.csvccdshustbe.service.medicineType;

import com.example.csvccdshustbe.dto.modules.medicineModules.medicineType.MedicineTypeDetailsDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineType.*;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeResponse;
import com.example.csvccdshustbe.response.medicineType.FindAllMedicineTypeVisibleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MedicineTypeService {

    Page<FindAllMedicineTypeVisibleResponse> findAllMedicineTypeVisibleResponse(FindAllMedicineTypeVisibleRequest request);
    Page<FindAllMedicineTypeResponse> findAllMedicineTypeResponse(FindAllMedicineTypeRequest request);

    void createMedicineType(CreateMedicineTypeRequest request) throws ValidateFiledException;

    void updateMedicineType(UpdateMedicineTypeRequest request) throws ValidateFiledException;

    void deleteMedicineTypeByIdMedicineType(Integer idMedicineType);

    void updateStatusMedicine(UpdateMedicineStatusRequest request) throws ValidateFiledException;

    List<MedicineTypeDetailsDto> findAllMedicineTypeToDownload();
}
