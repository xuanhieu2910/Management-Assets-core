package com.example.csvccdshustbe.service.original.shape.assetEvaluate;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;

import java.util.Map;

public interface OriginalAssetEvaluateService {

    ShapeOriginalAssetEvaluate save(ShapeOriginalAssetEvaluate assetEvaluate);

    Map<String, Object> findOriginalEvaluateById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetEvaluateById(Integer idInstance);

    ShapeOriginalAssetEvaluate findShapeOriginalAssetEvaluateById(Integer idInstance);
}
