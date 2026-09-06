package com.example.csvccdshustbe.repository.assetDeclare;

import com.example.csvccdshustbe.entity.AssetDeclare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDeclareRepository extends JpaRepository<AssetDeclare, Integer>, AssetDeclareRepositoryCustom {
}
