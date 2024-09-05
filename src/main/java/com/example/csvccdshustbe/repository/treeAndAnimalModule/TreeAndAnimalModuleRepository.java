package com.example.csvccdshustbe.repository.treeAndAnimalModule;

import com.example.csvccdshustbe.entity.AnimalTreeModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeAndAnimalModuleRepository extends JpaRepository<AnimalTreeModule, Integer>, TreeAndAnimalModuleRepositoryCustom {
}
