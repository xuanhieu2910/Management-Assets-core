package com.example.csvccdshustbe.repository.asset;

import com.example.csvccdshustbe.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer>, AssetRepositoryCustom {
}
