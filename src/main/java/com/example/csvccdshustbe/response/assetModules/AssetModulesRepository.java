package com.example.csvccdshustbe.response.assetModules;

import com.example.csvccdshustbe.entity.AssetModules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetModulesRepository extends JpaRepository<AssetModules, Integer>,AssetModulesRepositoryCustom {
}
