package com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetEvaluateDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetEvaluateRepositoryCustom {

    Optional<ShapeOriginalAssetEvaluateDetailsDto> findShapeOriginalAssetEvaluateDetailsDtoBuyId(Integer id);

}
