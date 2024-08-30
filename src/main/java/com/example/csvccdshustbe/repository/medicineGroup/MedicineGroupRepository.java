package com.example.csvccdshustbe.repository.medicineGroup;

import com.example.csvccdshustbe.entity.MedicineGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineGroupRepository extends JpaRepository<MedicineGroup, Integer>, MedicineGroupRepositoryCustom {
}
