package com.example.csvccdshustbe.service.original.shape.assetEvaluate.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate.ShapeOriginalAssetEvaluateRepository;
import com.example.csvccdshustbe.service.original.shape.assetEvaluate.OriginalAssetEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetEvaluateServiceImpl implements OriginalAssetEvaluateService {

    @Autowired
    ShapeOriginalAssetEvaluateRepository shapeOriginalAssetEvaluateRepository;

    @Override
    public ShapeOriginalAssetEvaluate save(ShapeOriginalAssetEvaluate assetEvaluate) {
        return shapeOriginalAssetEvaluateRepository.save(assetEvaluate);
    }
}
