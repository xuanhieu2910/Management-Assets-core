package com.example.csvccdshustbe.service.original.shape.assetGift;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;

import java.util.Map;

public interface OriginalAssetGiftService {

    ShapeOriginalAssetGift save(ShapeOriginalAssetGift shapeOriginalAssetGift);

    ShapeOriginalAssetGiftDetailsDto findOriginalAssetGiftById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetGiftById(Integer idInstance);

    ShapeOriginalAssetGift findShapeOriginalAssetGiftById(Integer idInstance);
}
