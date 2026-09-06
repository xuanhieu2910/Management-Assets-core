package com.example.csvccdshustbe.repository.carModule;

import com.example.csvccdshustbe.entity.CarModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModuleRepository extends JpaRepository<CarModule, Integer>, CarModuleRepositoryCustom {
}
