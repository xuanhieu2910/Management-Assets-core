package com.example.csvccdshustbe.service.original.noShape.transferLand.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransferLand.NoShapeOriginalAssetTransferLandRepository;
import com.example.csvccdshustbe.service.original.noShape.transferLand.NoOriginalAssetTransferLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetTransferLandServiceImpl implements NoOriginalAssetTransferLandService {

    @Autowired
    NoShapeOriginalAssetTransferLandRepository noShapeOriginalAssetTransferLandRepository;


    @Override
    public NoShapeOriginalAssetTransferLand save(NoShapeOriginalAssetTransferLand land) {
        return noShapeOriginalAssetTransferLandRepository.save(land);
    }
}
