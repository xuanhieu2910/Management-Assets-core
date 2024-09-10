package com.example.csvccdshustbe.service.assetOriginalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.AssetOriginalOfFormation;

import java.util.List;

public interface AssetOriginalOfFormationService {

    AssetOriginalOfFormation save(AssetOriginalOfFormation assetOriginalOfFormation);

    List<AssetOriginalOfFormation> saveAll(List<AssetOriginalOfFormation> assetOriginalOfFormation);
    List<AssetOriginalOfFormDto> findOriginalOfFormationByIdAsset(Integer idAsset);
}
