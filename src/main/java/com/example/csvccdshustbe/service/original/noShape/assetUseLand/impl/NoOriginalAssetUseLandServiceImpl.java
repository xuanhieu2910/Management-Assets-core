package com.example.csvccdshustbe.service.original.noShape.assetUseLand.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetUseLand.NoShapeOriginalAssetUseLandRepository;
import com.example.csvccdshustbe.service.original.noShape.assetUseLand.NoOriginalAssetUseLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetUseLandServiceImpl implements NoOriginalAssetUseLandService {


    @Autowired
    NoShapeOriginalAssetUseLandRepository noShapeOriginalAssetUseLandRepository;

    @Override
    public NoShapeOriginalAssetUseLand save(NoShapeOriginalAssetUseLand useLand) {
        return noShapeOriginalAssetUseLandRepository.save(useLand);
    }
}
