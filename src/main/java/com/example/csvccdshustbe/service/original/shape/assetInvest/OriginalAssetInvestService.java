package com.example.csvccdshustbe.service.original.shape.assetInvest;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetInvestDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;

import java.util.Map;

public interface OriginalAssetInvestService {

    ShapeOriginalAssetInvest save(ShapeOriginalAssetInvest shapeOriginalAssetInvest);

    ShapeOriginalAssetInvestDetailsDto findOriginalAssetInvestById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetInvestById(Integer idInstance);

    ShapeOriginalAssetInvest findShapeOriginalAssetInvestById(Integer idInstance);
}
