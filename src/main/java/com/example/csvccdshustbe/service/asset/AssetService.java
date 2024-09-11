package com.example.csvccdshustbe.service.asset;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface AssetService {

    void createAsset(Map<String,Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException;

    Page<FindAllAssetResponse> findAllAsset(FindAllAssetRequest request);


    Map<String, Object> findDetailsAssetByCodeAsset(String codeAsset);


}
