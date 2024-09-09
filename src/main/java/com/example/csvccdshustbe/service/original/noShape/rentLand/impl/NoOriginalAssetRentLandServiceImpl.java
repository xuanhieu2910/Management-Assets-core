package com.example.csvccdshustbe.service.original.noShape.rentLand.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetRentLand.NoShapeOriginalAssetRentLandRepository;
import com.example.csvccdshustbe.service.original.noShape.rentLand.NoOriginalAssetRentLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetRentLandServiceImpl implements NoOriginalAssetRentLandService {

    @Autowired
    NoShapeOriginalAssetRentLandRepository noShapeOriginalAssetRentLandRepository;

    @Override
    public NoShapeOriginalAssetRentLand save(NoShapeOriginalAssetRentLand land) {
        return noShapeOriginalAssetRentLandRepository.save(land);
    }
}
