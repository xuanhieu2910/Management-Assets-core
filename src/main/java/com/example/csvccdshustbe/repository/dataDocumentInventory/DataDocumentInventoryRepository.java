package com.example.csvccdshustbe.repository.dataDocumentInventory;

import com.example.csvccdshustbe.entity.DataDocumentInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDocumentInventoryRepository extends JpaRepository<DataDocumentInventory, Integer>,
        DataDocumentInventoryRepositoryCustom {
}
