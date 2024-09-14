package com.example.csvccdshustbe.service.original.noShape.rentLand;

import com.example.csvccdshustbe.dto.original.noShape.NoShapeOriginalAssetRentLandDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;

import java.util.Map;

public interface NoOriginalAssetRentLandService {

    NoShapeOriginalAssetRentLand save(NoShapeOriginalAssetRentLand land);

    NoShapeOriginalAssetRentLandDetailsDto findNoOriginalAssetRentLandById(Integer idInstance) throws IllegalAccessException;

    void deleteNoShapeOriginalAssetRendLandById(Integer idInstance);

    NoShapeOriginalAssetRentLand findNoShapeOriginalAssetRentLandById(Integer idInstance);
}
