package com.example.csvccdshustbe.service.original.shape.assetEvaluate.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetEvaluateDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.repository.shapeOriginalAssetEvaluate.ShapeOriginalAssetEvaluateRepository;
import com.example.csvccdshustbe.service.original.shape.assetEvaluate.OriginalAssetEvaluateService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetEvaluateServiceImpl implements OriginalAssetEvaluateService {

    @Autowired
    ShapeOriginalAssetEvaluateRepository shapeOriginalAssetEvaluateRepository;

    @Override
    public ShapeOriginalAssetEvaluate save(ShapeOriginalAssetEvaluate assetEvaluate) {
        return shapeOriginalAssetEvaluateRepository.save(assetEvaluate);
    }

    @Override
    public ShapeOriginalAssetEvaluateDetailsDto findOriginalEvaluateById(Integer idInstance) throws IllegalAccessException {
        Optional<ShapeOriginalAssetEvaluateDetailsDto> detailsDto =
                shapeOriginalAssetEvaluateRepository.findShapeOriginalAssetEvaluateDetailsDtoBuyId(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits original shape asset evaluate");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteShapeOriginalAssetEvaluateById(Integer idInstance) {
        shapeOriginalAssetEvaluateRepository.deleteOriginalAssetEvaluateById(idInstance);
    }

    @Override
    public ShapeOriginalAssetEvaluate findShapeOriginalAssetEvaluateById(Integer idInstance) {
        Optional<ShapeOriginalAssetEvaluate> shapeOriginalAssetEvaluate =
                shapeOriginalAssetEvaluateRepository.findShapeOriginalAssetEvaluateById(idInstance);
        if (shapeOriginalAssetEvaluate.isEmpty()){
            throw new NotFoundException("Don't exits shape original asset evaluate!");
        }
        return shapeOriginalAssetEvaluate.get();
    }
}
