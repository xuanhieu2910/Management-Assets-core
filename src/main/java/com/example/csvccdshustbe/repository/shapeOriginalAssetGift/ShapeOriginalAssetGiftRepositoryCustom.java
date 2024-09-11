package com.example.csvccdshustbe.repository.shapeOriginalAssetGift;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetGiftDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetGiftRepositoryCustom {

    Optional<ShapeOriginalAssetGiftDetailsDto> findShapeOriginalAssetGiftDetailsDtoById(Integer id);
}
