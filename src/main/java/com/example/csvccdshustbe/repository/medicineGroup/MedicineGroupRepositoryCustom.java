package com.example.csvccdshustbe.repository.medicineGroup;

import com.example.csvccdshustbe.entity.MedicineGroup;
import com.example.csvccdshustbe.request.medicineGroup.FindAllMedicineGroupRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MedicineGroupRepositoryCustom {

    Page<MedicineGroup> findAllMedicineGroupActive(FindAllMedicineGroupRequest request, Pageable pageable);
    Optional<MedicineGroup> findMedicineGroupByName(String name);
    Optional<MedicineGroup> findMedicineGroupById(Integer idMedicineType);
    List<MedicineGroup> findMedicineGroupByAllId(List<Integer> idMedicineType);
}
