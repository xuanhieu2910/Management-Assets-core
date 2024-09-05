package com.example.csvccdshustbe.repository.groundModule;

import com.example.csvccdshustbe.entity.GroundModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroundModuleRepository extends JpaRepository<GroundModule, Integer>, GroundModuleRepositoryCustom {
}
