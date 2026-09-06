package com.example.csvccdshustbe.repository.assetOriginal;

import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;

import java.util.Optional;

public interface AssetOriginalRepositoryCustom {
    Optional<BluePrintOriginalDto> findBluePrintAssetOriginalByIdAsset(Integer idAsset);

    void deleteAssetOriginalByIdOriginalAndIdInstance(Integer idOriginal, Integer idInstance);
}
