package com.example.csvccdshustbe.service.original.noShape.assetBuy;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;

import java.util.Map;

public interface NoOriginalAssetBuyService {

    NoShapeOriginalAssetBuy save(NoShapeOriginalAssetBuy assetBuy);

    Map<String, Object> findNoOriginalAssetBuyId(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetById(Integer idInstance);

    NoShapeOriginalAssetBuy findNoShapeOriginalAssetBuyById(Integer idInstance);
}
