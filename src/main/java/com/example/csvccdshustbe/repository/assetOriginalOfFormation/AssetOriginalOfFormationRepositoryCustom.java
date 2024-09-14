package com.example.csvccdshustbe.repository.assetOriginalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.AssetOriginalOfFormation;

import java.util.List;

public interface AssetOriginalOfFormationRepositoryCustom {


    List<AssetOriginalOfFormDto> findOriginalOfFormationDtoByIdAsset(Integer idAsset);
    List<AssetOriginalOfFormation> findOriginalOfFormationByIdAsset(Integer idAsset);
}
