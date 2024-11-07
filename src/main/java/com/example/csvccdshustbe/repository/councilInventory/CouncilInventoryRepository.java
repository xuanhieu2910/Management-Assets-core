package com.example.csvccdshustbe.repository.councilInventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouncilInventoryRepository extends JpaRepository<CouncilInventory, Integer>, CouncilInventoryRepositoryCustom {
}
