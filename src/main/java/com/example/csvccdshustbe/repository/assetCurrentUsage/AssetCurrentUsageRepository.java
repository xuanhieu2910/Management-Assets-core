package com.example.csvccdshustbe.repository.assetCurrentUsage;

import com.example.csvccdshustbe.entity.AssetCurrentUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetCurrentUsageRepository extends JpaRepository<AssetCurrentUsage, Integer>, AssetCurrentUsageRepositoryCustom {
}
