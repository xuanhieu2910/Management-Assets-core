package com.example.csvccdshustbe.service.original.shape.assetTransfer.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.repository.shapeOriginalAssetTransfer.ShapeOriginalAssetTransferRepository;
import com.example.csvccdshustbe.service.original.shape.assetTransfer.OriginalAssetTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetTransferServiceImpl implements OriginalAssetTransferService {

    @Autowired
    ShapeOriginalAssetTransferRepository shapeOriginalAssetTransferRepository;

    @Override
    public ShapeOriginalAssetTransfer save(ShapeOriginalAssetTransfer shapeOriginalAssetTransfer) {
        return shapeOriginalAssetTransferRepository.save(shapeOriginalAssetTransfer);
    }
}
