package com.example.csvccdshustbe.repository.typeBuyAsset;

import com.example.csvccdshustbe.entity.TypeBuyAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TypeBuyAssetRepository extends JpaRepository<TypeBuyAsset, Integer>, TypeBuyAssetRepositoryCustom{
}
