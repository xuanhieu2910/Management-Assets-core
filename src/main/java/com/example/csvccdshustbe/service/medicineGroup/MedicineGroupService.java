package com.example.csvccdshustbe.service.medicineGroup;

import com.example.csvccdshustbe.response.medicineGroup.FindAllMedicineGroupResponse;

import java.util.List;

public interface MedicineGroupService {

    List<FindAllMedicineGroupResponse> findAllMedicineGroupByStatus(Integer status);
}
