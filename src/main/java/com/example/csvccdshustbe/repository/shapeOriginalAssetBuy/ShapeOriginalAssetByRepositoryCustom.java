package com.example.csvccdshustbe.repository.shapeOriginalAssetBuy;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetBuyDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetByRepositoryCustom {

    Optional<ShapeOriginalAssetBuyDetailsDto> findOriginalAssetBuyDetailsDtoById (Integer idAssetBuy);
}
