package com.example.csvccdshustbe.repository.dataProcessAsset;

import com.example.csvccdshustbe.entity.DataProcessAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataProcessAssetRepository extends JpaRepository<DataProcessAsset, Integer>, DataProcessAssetRepositoryCustom {
}
