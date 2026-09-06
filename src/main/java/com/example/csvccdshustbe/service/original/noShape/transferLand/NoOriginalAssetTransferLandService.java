package com.example.csvccdshustbe.service.original.noShape.transferLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetTransferLandDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;

import java.util.Map;

public interface NoOriginalAssetTransferLandService {

    NoShapeOriginalAssetTransferLand save(NoShapeOriginalAssetTransferLand land);

    NoShapeOriginalAssetTransferLandDetailsDto findNoOriginalAssetTransferLandById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetTransferLandById(Integer idInstance);

    NoShapeOriginalAssetTransferLand findNoShapeOriginalAssetTransferLandById(Integer idInstance);
}
