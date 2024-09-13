package com.example.csvccdshustbe.service.original.shape.assetInvest;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;

import java.util.Map;

public interface OriginalAssetInvestService {

    ShapeOriginalAssetInvest save(ShapeOriginalAssetInvest shapeOriginalAssetInvest);

    Map<String, Object> findOriginalAssetInvestById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetInvestById(Integer idInstance);
}
