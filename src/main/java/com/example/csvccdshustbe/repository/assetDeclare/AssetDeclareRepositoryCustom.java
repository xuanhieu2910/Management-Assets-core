package com.example.csvccdshustbe.repository.assetDeclare;

import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;

import java.util.Optional;

public interface AssetDeclareRepositoryCustom {
    Optional<BluePrintDeclareDto> findBluePrintAssetDeclareDtoByIdAsset(Integer idAsset);

    void deleteAssetDeclareByIdInstanceAndIdDeclare(Integer idInstance, Integer idDeclare);
}
