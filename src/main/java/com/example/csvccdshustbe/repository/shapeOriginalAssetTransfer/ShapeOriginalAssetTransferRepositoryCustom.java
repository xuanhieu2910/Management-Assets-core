package com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetTransferDetailsDto;

import java.util.Optional;

public interface ShapeOriginalAssetTransferRepositoryCustom {


    Optional<ShapeOriginalAssetTransferDetailsDto> findShapeOriginalAssetTransferDetailsDtoById(Integer id);
}
