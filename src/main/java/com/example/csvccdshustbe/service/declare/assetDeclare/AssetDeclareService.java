package com.example.csvccdshustbe.service.declare.assetDeclare;

import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.entity.AssetDeclare;

public interface AssetDeclareService {

    AssetDeclare save(AssetDeclare declare);

    BluePrintDeclareDto findBluePrintAssetDeclareByIdAsset(Integer idAsset);

    void deleteAssetDeclareByIdInstanceAndIdDeclare(Integer idInstance, Integer idDeclare);
}
