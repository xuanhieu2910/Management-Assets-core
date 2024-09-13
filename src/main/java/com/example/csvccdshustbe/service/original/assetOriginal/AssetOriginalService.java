package com.example.csvccdshustbe.service.original.assetOriginal;


import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
import com.example.csvccdshustbe.entity.AssetOriginal;

public interface AssetOriginalService {

    AssetOriginal save(AssetOriginal assetOriginal);

    BluePrintOriginalDto findBluePrintAssetOriginalByIdAsset(Integer idAsset);

    void deleteAssetOriginalByIdOriginalAndIdInstance(Integer idOriginal, Integer idInstance);
}
