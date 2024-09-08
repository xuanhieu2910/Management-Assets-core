package com.example.csvccdshustbe.repository.typeDeclareAsset;

import com.example.csvccdshustbe.entity.TypeDeclareAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDeclareAssetRepository extends JpaRepository<TypeDeclareAsset, Integer>, TypeDeclareAssetRepositoryCustom {
}
