package com.example.csvccdshustbe.repository.medicineGroup;

import com.example.csvccdshustbe.entity.MedicineGroup;
import com.example.csvccdshustbe.entity.Suppliers;

import java.util.List;
import java.util.Optional;

public interface MedicineGroupRepositoryCustom {

    List<MedicineGroup> findAllMedicineGroupByStatus(Integer status);
    Optional<MedicineGroup> findMedicineGroupByName(String name);
    Optional<MedicineGroup> findMedicineGroupById(Integer idMedicineType);
}
