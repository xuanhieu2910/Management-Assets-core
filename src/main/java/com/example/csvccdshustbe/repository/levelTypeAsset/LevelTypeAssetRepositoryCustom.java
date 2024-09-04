package com.example.csvccdshustbe.repository.levelTypeAsset;

import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.entity.Suppliers;

import java.util.List;
import java.util.Optional;

public interface LevelTypeAssetRepositoryCustom {
    List<LevelTypeAsset>findAllLevelTypeAssetByStatus(Integer Status);
    Optional<LevelTypeAsset> findLevelTypeAssetByName(String name);
    Optional<LevelTypeAsset> findLevelTypeAssetById(Integer idLevelTypeAsset);
}
