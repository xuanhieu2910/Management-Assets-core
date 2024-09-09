package com.example.csvccdshustbe.repository.noShapeOriginalAssetRentLand;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetRentLandRepository extends JpaRepository<NoShapeOriginalAssetRentLand, Integer>,
        NoShapeOriginalAssetRentLandRepositoryCustom {
}
