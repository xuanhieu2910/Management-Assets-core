package com.example.csvccdshustbe.repository.shapeOriginalAssetGift;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;
import com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate.ShapeOriginalAssetEvaluateRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetGiftRepository extends JpaRepository<ShapeOriginalAssetGift, Integer>,
        ShapeOriginalAssetGiftRepositoryCustom {
}
