package com.example.csvccdshustbe.service.assetProcess;

import com.example.csvccdshustbe.entity.AssetProcess;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.response.assetProcess.FindAllAssetProcessResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetProcessService {
    List<AssetProcess> saveListAssetProcess(List<AssetProcess> assetProcessList);
    Page<FindAllAssetProcessResponse> findAllAssetProcess(FindAllAssetProcessRequest request);
}
