package com.example.csvccdshustbe.service.original.shape.assetTransfer;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;

import java.util.Map;

public interface OriginalAssetTransferService {

    ShapeOriginalAssetTransfer save(ShapeOriginalAssetTransfer shapeOriginalAssetTransfer);

    Map<String, Object> findOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetTransfer(Integer idInstance);
}
