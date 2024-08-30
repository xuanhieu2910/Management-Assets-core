package com.example.csvccdshustbe.repository.medicineGroup;

import com.example.csvccdshustbe.entity.MedicineGroup;

import java.util.List;

public interface MedicineGroupRepositoryCustom {

    List<MedicineGroup> findAllMedicineGroupByStatus(Integer status);
}
