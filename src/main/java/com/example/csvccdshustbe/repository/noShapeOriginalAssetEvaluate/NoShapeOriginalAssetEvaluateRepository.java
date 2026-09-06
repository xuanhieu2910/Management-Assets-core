package com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoShapeOriginalAssetEvaluateRepository extends JpaRepository<NoShapeOriginalAssetEvaluate, Integer>,
                 NoShapeOriginalAssetEvaluateRepositoryCustom{
}
