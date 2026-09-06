package com.example.csvccdshustbe.service.original.shape.assetBuy;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;

import java.util.Map;

public interface OriginalAssetBuyService {

    ShapeOriginalAssetBuy save(ShapeOriginalAssetBuy assetBuy);

    ShapeOriginalAssetBuyDetailsDto findOriginalAssetBuyId(Integer idOriginalAssetBuy) throws IllegalAccessException;

    void deleteShapeOriginalAssetById(Integer idInstance);

    ShapeOriginalAssetBuy findShapeOriginalAssetBuyById(Integer idInstance);
}
