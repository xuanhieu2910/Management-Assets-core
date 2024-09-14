package com.example.csvccdshustbe.service.original.noShape.assetUseLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetUseLandDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;

import java.util.Map;

public interface NoOriginalAssetUseLandService {

    NoShapeOriginalAssetUseLand save(NoShapeOriginalAssetUseLand useLand);

    NoShapeOriginalAssetUseLandDetailsDto findNoOriginalAssetUseLandById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetUseLandById(Integer idInstance);

    NoShapeOriginalAssetUseLand findNoShapeOriginalAssetUseLandById(Integer idInstance);
}
