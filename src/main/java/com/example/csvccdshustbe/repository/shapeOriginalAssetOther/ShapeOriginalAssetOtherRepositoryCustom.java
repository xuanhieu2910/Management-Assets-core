package com.example.csvccdshustbe.repository.shapeOriginalAssetOther;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetOther;

import java.util.Optional;

public interface ShapeOriginalAssetOtherRepositoryCustom {
    Optional<ShapeOriginalAssetOtherDetailsDto> findOriginalConnectActorDetailsDtoById(Integer idInstance);

    void deleteShapeOriginalAssetOtherById(Integer idInstance);

    Optional<ShapeOriginalAssetOther> findOriginalConnectActorDetailsById(Integer idInstance);
}
