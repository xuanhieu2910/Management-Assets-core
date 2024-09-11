package com.example.csvccdshustbe.service.original.noShape.rentLand;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;

import java.util.Map;

public interface NoOriginalAssetRentLandService {

    NoShapeOriginalAssetRentLand save(NoShapeOriginalAssetRentLand land);

    Map<String, Object> findNoOriginalAssetRentLandById(Integer idInstance) throws IllegalAccessException;
}
