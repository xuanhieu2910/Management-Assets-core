package com.example.csvccdshustbe.service.original.shape.assetGift.impl;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetGiftDetailsDto;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;
import com.example.csvccdshustbe.repository.shapeOriginalAssetGift.ShapeOriginalAssetGiftRepository;
import com.example.csvccdshustbe.service.original.shape.assetGift.OriginalAssetGiftService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OriginalAssetGiftServiceImpl implements OriginalAssetGiftService {

    @Autowired
    ShapeOriginalAssetGiftRepository shapeOriginalAssetGiftRepository;


    @Override
    public ShapeOriginalAssetGift save(ShapeOriginalAssetGift shapeOriginalAssetGift) {
        return shapeOriginalAssetGiftRepository.save(shapeOriginalAssetGift);
    }

    @Override
    public ShapeOriginalAssetGiftDetailsDto findOriginalAssetGiftById(Integer idInstance) throws IllegalAccessException {
        Optional<ShapeOriginalAssetGiftDetailsDto> detailsDto =
                shapeOriginalAssetGiftRepository.findShapeOriginalAssetGiftDetailsDtoById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits asset gift!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteShapeOriginalAssetGiftById(Integer idInstance) {
        shapeOriginalAssetGiftRepository.deleteShapeOriginalAssetGiftById(idInstance);
    }

    @Override
    public ShapeOriginalAssetGift findShapeOriginalAssetGiftById(Integer idInstance) {
        Optional<ShapeOriginalAssetGift> shapeOriginalAssetGift =
                shapeOriginalAssetGiftRepository.findShapeOriginalAssetGiftById(idInstance);
        if (shapeOriginalAssetGift.isEmpty()){
            throw new NotFoundException("Don't exits shape original asset gift!");
        }
        return shapeOriginalAssetGift.get();
    }
}
