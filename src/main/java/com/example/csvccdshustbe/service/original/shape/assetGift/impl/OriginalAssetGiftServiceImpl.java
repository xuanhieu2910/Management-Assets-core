package com.example.csvccdshustbe.service.original.shape.assetGift.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;
import com.example.csvccdshustbe.repository.shapeOriginalAssetGift.ShapeOriginalAssetGiftRepository;
import com.example.csvccdshustbe.service.original.shape.assetGift.OriginalAssetGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetGiftServiceImpl implements OriginalAssetGiftService {

    @Autowired
    ShapeOriginalAssetGiftRepository shapeOriginalAssetGiftRepository;


    @Override
    public ShapeOriginalAssetGift save(ShapeOriginalAssetGift shapeOriginalAssetGift) {
        return shapeOriginalAssetGiftRepository.save(shapeOriginalAssetGift);
    }
}
