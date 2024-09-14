package com.example.csvccdshustbe.service.assetOriginalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.AssetOriginalOfFormation;

import java.util.List;

public interface AssetOriginalOfFormationService {

    AssetOriginalOfFormation save(AssetOriginalOfFormation assetOriginalOfFormation);

    List<AssetOriginalOfFormation> saveAll(List<AssetOriginalOfFormation> assetOriginalOfFormation);
    List<AssetOriginalOfFormDto> findOriginalOfFormationDtoByIdAsset(Integer idAsset);
    List<AssetOriginalOfFormation> findOriginalOfFormationByIdAsset(Integer idAsset);

    void deleteAssetOriginalOfFormation(AssetOriginalOfFormation original);
}
