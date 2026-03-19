package com.example.csvccdshustbe.service.original.noShape.assetOther;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetOtherDetailsDto;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetOther;



public interface NoOriginalAssetOtherService {
    NoShapeOriginalAssetOther save(NoShapeOriginalAssetOther noShapeOriginalAssetOther);

    NoShapeOriginalAssetOtherDetailsDto findNoOriginalConnectActorById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetOtherById(Integer idInstance);

    NoShapeOriginalAssetOther findNoShapeOriginalAssetOtherById(Integer idInstance);
}
