package com.example.csvccdshustbe.repository.shapeOriginalAssetBuy;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;

import java.util.Optional;

public interface ShapeOriginalAssetByRepositoryCustom {

    Optional<ShapeOriginalAssetBuyDetailsDto> findOriginalAssetBuyDetailsDtoById (Integer idAssetBuy);

    void deleteShapeOriginalAssetById(Integer idInstance);

    Optional<ShapeOriginalAssetBuy> findShapeOriginalAssetBuyById(Integer idInstance);
}
