package com.example.csvccdshustbe.repository.fluctuatingSituationAssetRepository;

import com.example.csvccdshustbe.entity.FluctuatingSituationAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FluctuatingSituationAssetRepository extends JpaRepository<FluctuatingSituationAsset, Integer>,
                FluctuatingSituationAssetRepositoryCustom{

}
