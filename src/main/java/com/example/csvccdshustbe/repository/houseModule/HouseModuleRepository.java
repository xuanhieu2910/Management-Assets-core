package com.example.csvccdshustbe.repository.houseModule;

import com.example.csvccdshustbe.entity.HouseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseModuleRepository extends JpaRepository<HouseModule, Integer>, HouseModuleRepositoryCustom {
}
