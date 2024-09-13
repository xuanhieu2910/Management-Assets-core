package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectActor;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectActorDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetConnectActorRepositoryCustom {


    Optional<ShapeOriginalAssetConnectActorDetailsDto> findShapeOriginalAssetConnectActorDetailsDtoById (Integer id);

    void deleteShapeOriginalAssetConnectActorById(Integer idInstance);
}
