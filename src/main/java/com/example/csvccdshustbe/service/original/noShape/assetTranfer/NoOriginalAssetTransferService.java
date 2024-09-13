package com.example.csvccdshustbe.service.original.noShape.assetTranfer;

import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;

import java.util.Map;

public interface NoOriginalAssetTransferService {

    NoShapeOriginalAssetTransfer save(NoShapeOriginalAssetTransfer transfer);

    Map<String, Object> findNoOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetTransferById(Integer idInstance);
}
