package com.example.csvccdshustbe.service.original.noShape.assetEvaluate.impl;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetEvaluateDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetEvaluate.NoShapeOriginalAssetEvaluateRepository;
import com.example.csvccdshustbe.service.original.noShape.assetEvaluate.NoOriginalAssetEvaluateService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class NoOriginalAssetEvaluateServiceImpl implements NoOriginalAssetEvaluateService {

    @Autowired
    NoShapeOriginalAssetEvaluateRepository noShapeOriginalAssetEvaluateRepository;

    @Override
    public NoShapeOriginalAssetEvaluate save(NoShapeOriginalAssetEvaluate evaluate) {
        return noShapeOriginalAssetEvaluateRepository.save(evaluate);
    }

    @Override
    public Map<String, Object> findNoOriginalAssetEvaluateById(Integer idInstance) throws IllegalAccessException {
        Optional<NoShapeOriginalAssetEvaluateDetailsDto> detailsDto =
                noShapeOriginalAssetEvaluateRepository.findNoShapeOriginalAssetEvaluateDetailsDto(idInstance);
        if (!detailsDto.isPresent()){
            throw new NotFoundException("Don't exits no shape original asset evaluate!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteNoShapeOriginalAssetEvaluateById(Integer idInstance) {
        noShapeOriginalAssetEvaluateRepository.deleteNoShapeOriginalAssetEvaluateById(idInstance);
    }
}
