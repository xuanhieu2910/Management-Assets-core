package com.example.csvccdshustbe.repository.asset;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.asset.FindDetailsAssetDto;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssetRepositoryCustom {

    Page<FindAllAssetDto> findAllAssetDto(FindAllAssetRequest request, Pageable pageable);

    Optional<AssetBluePrintDto> findDetailAssetByCodeAsset(String codeAsset);
}
