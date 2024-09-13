package com.example.csvccdshustbe.service.original.noShape.transferLand;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;

import java.util.Map;

public interface NoOriginalAssetTransferLandService {

    NoShapeOriginalAssetTransferLand save(NoShapeOriginalAssetTransferLand land);

    Map<String, Object> findNoOriginalAssetTransferLandById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapOriginalAssetTransferLandById(Integer idInstance);
}
