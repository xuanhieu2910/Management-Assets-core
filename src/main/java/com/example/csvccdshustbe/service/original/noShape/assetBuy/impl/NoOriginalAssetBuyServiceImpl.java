package com.example.csvccdshustbe.service.original.noShape.assetBuy.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetBuy.NoShapeOriginalAssetBuyRepository;
import com.example.csvccdshustbe.service.original.noShape.assetBuy.NoOriginalAssetBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetBuyServiceImpl implements NoOriginalAssetBuyService {

    @Autowired
    NoShapeOriginalAssetBuyRepository noShapeOriginalAssetBuyRepository;


    @Override
    public NoShapeOriginalAssetBuy save(NoShapeOriginalAssetBuy assetBuy) {
        return noShapeOriginalAssetBuyRepository.save(assetBuy);
    }
}
