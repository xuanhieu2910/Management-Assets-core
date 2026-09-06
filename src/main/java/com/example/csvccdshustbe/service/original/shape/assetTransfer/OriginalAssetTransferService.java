package com.example.csvccdshustbe.service.original.shape.assetTransfer;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;

import java.util.Map;

public interface OriginalAssetTransferService {

    ShapeOriginalAssetTransfer save(ShapeOriginalAssetTransfer shapeOriginalAssetTransfer);

    ShapeOriginalAssetTransferDetailsDto findOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetTransfer(Integer idInstance);

    ShapeOriginalAssetTransfer findShapeOriginalAssetTransferById(Integer idInstance);
}
