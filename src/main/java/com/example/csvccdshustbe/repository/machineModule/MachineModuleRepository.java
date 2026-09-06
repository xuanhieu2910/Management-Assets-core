package com.example.csvccdshustbe.repository.machineModule;

import com.example.csvccdshustbe.entity.MachineModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineModuleRepository extends JpaRepository<MachineModule, Integer>, MachineModuleRepositoryCustom {
}
