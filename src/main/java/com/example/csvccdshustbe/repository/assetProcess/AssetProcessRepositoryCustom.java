package com.example.csvccdshustbe.repository.assetProcess;

import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssetProcessRepositoryCustom {
    Page<FindAllAssetDto> findAllAssetProcess(FindAllAssetProcessRequest request, Pageable pageable);
}
