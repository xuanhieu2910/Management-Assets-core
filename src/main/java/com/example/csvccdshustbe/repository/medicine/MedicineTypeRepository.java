package com.example.csvccdshustbe.repository.medicine;

import com.example.csvccdshustbe.entity.MedicineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineTypeRepository extends JpaRepository<MedicineType, Integer>, MedicineTypeRepositoryCustom {
}
