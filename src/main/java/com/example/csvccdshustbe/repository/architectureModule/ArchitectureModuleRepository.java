package com.example.csvccdshustbe.repository.architectureModule;

import com.example.csvccdshustbe.entity.ArchitectureModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchitectureModuleRepository extends JpaRepository<ArchitectureModule, Integer>,
        ArchitectureModuleRepositoryCustom {
}
