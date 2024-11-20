package com.example.csvccdshustbe.repository.assetProcess;

import com.example.csvccdshustbe.entity.AssetProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetProcessRepository extends JpaRepository<AssetProcess, Integer>,
        AssetProcessRepositoryCustom {
}
