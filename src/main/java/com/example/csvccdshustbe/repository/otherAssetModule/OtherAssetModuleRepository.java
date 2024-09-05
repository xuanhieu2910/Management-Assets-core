package com.example.csvccdshustbe.repository.otherAssetModule;

import com.example.csvccdshustbe.entity.OtherAssetModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherAssetModuleRepository extends JpaRepository<OtherAssetModule, Integer>, OtherAssetModuleRepositoryCustom {
}
