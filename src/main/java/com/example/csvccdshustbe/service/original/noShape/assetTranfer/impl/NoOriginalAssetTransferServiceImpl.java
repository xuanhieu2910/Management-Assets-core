package com.example.csvccdshustbe.service.original.noShape.assetTranfer.impl;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.repository.noShapeOriginalAssetTransfer.NoShapeOriginalAssetTransferRepository;
import com.example.csvccdshustbe.service.original.noShape.assetTranfer.NoOriginalAssetTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoOriginalAssetTransferServiceImpl implements NoOriginalAssetTransferService {

    @Autowired
    NoShapeOriginalAssetTransferRepository shapeOriginalAssetTransferRepository;

    @Override
    public NoShapeOriginalAssetTransfer save(NoShapeOriginalAssetTransfer transfer) {
        return shapeOriginalAssetTransferRepository.save(transfer);
    }
}
