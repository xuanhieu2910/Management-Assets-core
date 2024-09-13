package com.example.csvccdshustbe.repository.shapeOriginalAssetGift;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;

import java.util.Optional;

public interface ShapeOriginalAssetGiftRepositoryCustom {

    Optional<ShapeOriginalAssetGiftDetailsDto> findShapeOriginalAssetGiftDetailsDtoById(Integer id);

    void deleteShapeOriginalAssetGiftById(Integer idInstance);

    Optional<ShapeOriginalAssetGift> findShapeOriginalAssetGiftById(Integer idInstance);
}
