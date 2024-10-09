package com.example.csvccdshustbe.service.medicineGroup;

import com.example.csvccdshustbe.dto.modules.medicineModules.medicineGroup.MedicineGroupDetailsDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.medicineGroup.CreateMedicineGroupRequest;
import com.example.csvccdshustbe.request.medicineGroup.FindAllMedicineGroupRequest;
import com.example.csvccdshustbe.request.medicineGroup.UpdateMedicineGroupRequest;
import com.example.csvccdshustbe.response.medicineGroup.FindAllMedicineGroupResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MedicineGroupService {

    Page<FindAllMedicineGroupResponse> findAllMedicineGroup(FindAllMedicineGroupRequest findAllMedicineGroupRequest);
    void createMedicineGroup(CreateMedicineGroupRequest request) throws ValidateFiledException;

    void updateMedicineGroup(UpdateMedicineGroupRequest request) throws ValidateFiledException;

    void deleteMedicineGroupByIdMedicineGroup(Integer idMedicineGroup);

    List<MedicineGroupDetailsDto> findAllMedicineGroupToDownload();
}
