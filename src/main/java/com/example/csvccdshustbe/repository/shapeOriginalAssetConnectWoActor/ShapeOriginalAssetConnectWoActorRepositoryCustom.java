package com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectWoActorDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;

import java.util.Optional;

public interface ShapeOriginalAssetConnectWoActorRepositoryCustom {

    Optional<ShapeOriginalAssetConnectWoActorDetailsDto> findShapeOriginalAssetConnectWoActorDetailsDtoById(Integer id);

    void deleteShapeOriginalAssetConnectWoActorById(Integer idInstance);

    Optional<ShapeOriginalAssetConnectWoActor> findShapeOriginalAssetConnectWoActorById(Integer idInstance);
}
