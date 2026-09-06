package com.example.csvccdshustbe.repository.medicineModule;

import com.example.csvccdshustbe.entity.MedicineModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineModuleRepository extends JpaRepository<MedicineModule, Integer>,MedicineModuleRepositoryCustom {
}
