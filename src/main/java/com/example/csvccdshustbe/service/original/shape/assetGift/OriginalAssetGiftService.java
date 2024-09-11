package com.example.csvccdshustbe.service.original.shape.assetGift;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;

import java.util.Map;

public interface OriginalAssetGiftService {

    ShapeOriginalAssetGift save(ShapeOriginalAssetGift shapeOriginalAssetGift);

    Map<String, Object> findOriginalAssetGiftById(Integer idInstance);
}
