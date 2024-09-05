package com.example.csvccdshustbe.service.original.shape.assetBuy.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;
import com.example.csvccdshustbe.repository.shapeOriginalAssetBuy.ShapeOriginalAssetByRepository;
import com.example.csvccdshustbe.service.original.shape.assetBuy.OriginalAssetBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetBuyServiceImpl implements OriginalAssetBuyService {

    @Autowired
    ShapeOriginalAssetByRepository shapeOriginalAssetByRepository;
    @Override
    public ShapeOriginalAssetBuy save(ShapeOriginalAssetBuy assetBuy) {
        return shapeOriginalAssetByRepository.save(assetBuy);
    }
}
