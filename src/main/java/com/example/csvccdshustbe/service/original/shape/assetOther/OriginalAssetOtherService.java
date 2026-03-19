package com.example.csvccdshustbe.service.original.shape.assetOther;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetOther;

public interface OriginalAssetOtherService {
    ShapeOriginalAssetOther save(ShapeOriginalAssetOther shapeOriginalAssetOther);

    ShapeOriginalAssetOtherDetailsDto findOriginalConnectActorById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetOtherById(Integer idInstance);

    ShapeOriginalAssetOther findShapeOriginalAssetOtherById(Integer idInstance);
}
