package com.example.csvccdshustbe.service.original.noShape.assetTranfer;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;

import java.util.Map;

public interface NoOriginalAssetTransferService {

    NoShapeOriginalAssetTransfer save(NoShapeOriginalAssetTransfer transfer);

    NoShapeOriginalAssetTransferDetailsDto findNoOriginalAssetTransferById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetTransferById(Integer idInstance);

    NoShapeOriginalAssetTransfer findNoShapeOriginalAssetTransferById(Integer idInstance);
}
