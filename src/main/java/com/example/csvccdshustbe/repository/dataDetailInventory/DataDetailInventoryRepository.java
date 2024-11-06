package com.example.csvccdshustbe.repository.dataDetailInventory;

import com.example.csvccdshustbe.entity.DataDetailInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDetailInventoryRepository extends JpaRepository<DataDetailInventory, Integer>,
        DataDetailInventoryRepositoryCustom {
}
