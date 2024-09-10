package com.example.csvccdshustbe.repository.assetOriginalOfFormation;

import com.example.csvccdshustbe.entity.AssetOriginalOfFormation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetOriginalOfFormationRepository extends JpaRepository<AssetOriginalOfFormation,Integer>,
        AssetOriginalOfFormationRepositoryCustom {
}
