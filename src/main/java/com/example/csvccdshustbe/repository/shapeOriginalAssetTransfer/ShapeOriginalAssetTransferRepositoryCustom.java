package com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;

import java.util.Optional;

public interface ShapeOriginalAssetTransferRepositoryCustom {


    Optional<ShapeOriginalAssetTransferDetailsDto> findShapeOriginalAssetTransferDetailsDtoById(Integer id);

    void deleteShapeOriginalAssetTransferById(Integer idInstance);

    Optional<ShapeOriginalAssetTransfer> findShapeOriginalAssetTransferById(Integer idInstance);
}
