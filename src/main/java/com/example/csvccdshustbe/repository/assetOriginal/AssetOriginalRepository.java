package com.example.csvccdshustbe.repository.assetOriginal;

import com.example.csvccdshustbe.entity.AssetOriginal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetOriginalRepository extends JpaRepository<AssetOriginal, Integer>, AssetOriginalRepositoryCustom {
}
