package com.example.csvccdshustbe.service.asset;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface AssetService {

    void createAsset(Map<String,Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException;

}
