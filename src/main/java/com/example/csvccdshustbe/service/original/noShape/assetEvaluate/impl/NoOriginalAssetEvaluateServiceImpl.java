package com.example.csvccdshustbe.service.original.noShape.assetEvaluate.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate.NoShapeOriginalAssetEvaluateRepository;
import com.example.csvccdshustbe.service.original.noShape.assetEvaluate.NoOriginalAssetEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetEvaluateServiceImpl implements NoOriginalAssetEvaluateService {

    @Autowired
    NoShapeOriginalAssetEvaluateRepository shapeOriginalAssetEvaluateRepository;

    @Override
    public NoShapeOriginalAssetEvaluate save(NoShapeOriginalAssetEvaluate evaluate) {
        return shapeOriginalAssetEvaluateRepository.save(evaluate);
    }
}
