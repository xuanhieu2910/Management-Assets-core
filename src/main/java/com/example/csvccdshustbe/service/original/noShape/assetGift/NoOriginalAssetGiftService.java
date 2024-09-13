package com.example.csvccdshustbe.service.original.noShape.assetGift;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetGift;

import java.util.Map;

public interface NoOriginalAssetGiftService {

    NoShapeOriginalAssetGift save(NoShapeOriginalAssetGift gift);

    Map<String, Object> findNoOriginalAssetGiftById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetGiftById(Integer idInstance);
}
