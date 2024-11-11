package com.example.csvccdshustbe.repository.assetInstance;

import com.example.csvccdshustbe.entity.AssetInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetInstanceRepository extends JpaRepository<AssetInstance, Integer>, AssetInstanceRepositoryCustom {
}
