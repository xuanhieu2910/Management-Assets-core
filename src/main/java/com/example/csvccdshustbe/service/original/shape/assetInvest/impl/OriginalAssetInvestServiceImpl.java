package com.example.csvccdshustbe.service.original.shape.assetInvest.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;
import com.example.csvccdshustbe.repository.shapeOriginalAssetInvest.ShapeOriginalAssetInvestRepository;
import com.example.csvccdshustbe.service.original.shape.assetInvest.OriginalAssetInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetInvestServiceImpl implements OriginalAssetInvestService {

    @Autowired
    ShapeOriginalAssetInvestRepository shapeOriginalAssetInvestRepository;

    @Override
    public ShapeOriginalAssetInvest save(ShapeOriginalAssetInvest shapeOriginalAssetInvest) {
        return shapeOriginalAssetInvestRepository.save(shapeOriginalAssetInvest);
    }
}
