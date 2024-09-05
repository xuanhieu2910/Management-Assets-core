package com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShapeOriginalAssetEvaluateRepository extends JpaRepository<ShapeOriginalAssetEvaluate, Integer>,
        ShapeOriginalAssetEvaluateRepositoryCustom{
}
