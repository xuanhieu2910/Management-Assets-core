package com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetEvaluateDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;

import java.util.Optional;

public interface ShapeOriginalAssetEvaluateRepositoryCustom {

    Optional<ShapeOriginalAssetEvaluateDetailsDto> findShapeOriginalAssetEvaluateDetailsDtoBuyId(Integer id);

    void deleteOriginalAssetEvaluateById(Integer idInstance);

    Optional<ShapeOriginalAssetEvaluate> findShapeOriginalAssetEvaluateById(Integer idInstance);
}
