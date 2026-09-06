package com.example.csvccdshustbe.repository.assetDeclare;

import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.dto.report.CurrentUsageReport08aDto;

import java.util.List;
import java.util.Optional;

public interface AssetDeclareRepositoryCustom {
    Optional<BluePrintDeclareDto> findBluePrintAssetDeclareDtoByIdAsset(Integer idAsset);

    void deleteAssetDeclareByIdInstanceAndIdDeclare(Integer idInstance, Integer idDeclare);


}
