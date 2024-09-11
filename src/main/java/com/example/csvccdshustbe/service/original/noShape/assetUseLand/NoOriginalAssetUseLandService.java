package com.example.csvccdshustbe.service.original.noShape.assetUseLand;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;

import java.util.Map;

public interface NoOriginalAssetUseLandService {

    NoShapeOriginalAssetUseLand save(NoShapeOriginalAssetUseLand useLand);

    Map<String, Object> findNoOriginalAssetUseLandById(Integer idInstance);
}
