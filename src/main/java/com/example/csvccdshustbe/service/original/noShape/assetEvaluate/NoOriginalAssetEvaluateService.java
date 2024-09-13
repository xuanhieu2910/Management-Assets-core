package com.example.csvccdshustbe.service.original.noShape.assetEvaluate;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;

import java.util.Map;

public interface NoOriginalAssetEvaluateService {

    NoShapeOriginalAssetEvaluate save(NoShapeOriginalAssetEvaluate evaluate);

    Map<String, Object> findNoOriginalAssetEvaluateById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetEvaluateById(Integer idInstance);

    NoShapeOriginalAssetEvaluate findNoShapeOriginalAssetEvaluateById(Integer idInstance);
}
