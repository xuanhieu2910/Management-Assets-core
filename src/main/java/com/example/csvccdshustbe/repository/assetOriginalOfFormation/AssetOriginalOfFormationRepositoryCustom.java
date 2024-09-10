package com.example.csvccdshustbe.repository.assetOriginalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;

import java.util.List;

public interface AssetOriginalOfFormationRepositoryCustom {


    List<AssetOriginalOfFormDto> findOriginalOfFormationByIdAsset(Integer idAsset);
}
