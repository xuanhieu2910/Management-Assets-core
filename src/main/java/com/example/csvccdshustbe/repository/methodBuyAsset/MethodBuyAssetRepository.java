package com.example.csvccdshustbe.repository.methodBuyAsset;

import com.example.csvccdshustbe.entity.MethodBuyAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodBuyAssetRepository extends JpaRepository<MethodBuyAsset, Integer>, MethodBuyAssetRepositoryCustom {
}
