package com.example.csvccdshustbe.repository.levelTypeAsset;

import com.example.csvccdshustbe.entity.LevelTypeAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelTypeAssetRepository extends JpaRepository<LevelTypeAsset,Integer>,LevelTypeAssetRepositoryCustom {
}
