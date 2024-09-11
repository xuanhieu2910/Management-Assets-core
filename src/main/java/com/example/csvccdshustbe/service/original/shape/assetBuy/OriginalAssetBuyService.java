package com.example.csvccdshustbe.service.original.shape.assetBuy;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;

import java.util.Map;

public interface OriginalAssetBuyService {

    ShapeOriginalAssetBuy save(ShapeOriginalAssetBuy assetBuy);

    Map<String, Object> findOriginalAssetBuyId(Integer idOriginalAssetBuy) throws IllegalAccessException;

}
