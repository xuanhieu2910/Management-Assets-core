package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectWoActorDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetConnectWoActorRepositoryCustom {

    Optional<ShapeOriginalAssetConnectWoActorDetailsDto> findShapeOriginalAssetConnectWoActorDetailsDtoById(Integer id);
}
