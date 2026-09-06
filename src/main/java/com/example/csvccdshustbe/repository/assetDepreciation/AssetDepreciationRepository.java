package com.example.csvccdshustbe.repository.assetDepreciation;

import com.example.csvccdshustbe.entity.AssetDepreciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDepreciationRepository extends JpaRepository<AssetDepreciation, Integer>, AssetDepreciationRepositoryCustom {
}
