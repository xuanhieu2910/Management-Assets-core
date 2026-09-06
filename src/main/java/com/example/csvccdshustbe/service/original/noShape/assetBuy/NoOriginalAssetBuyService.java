package com.example.csvccdshustbe.service.original.noShape.assetBuy;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetBuyDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;

import java.util.Map;

public interface NoOriginalAssetBuyService {

    NoShapeOriginalAssetBuy save(NoShapeOriginalAssetBuy assetBuy);

    NoShapeOriginalAssetBuyDetailsDto findNoOriginalAssetBuyId(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetById(Integer idInstance);

    NoShapeOriginalAssetBuy findNoShapeOriginalAssetBuyById(Integer idInstance);
}
