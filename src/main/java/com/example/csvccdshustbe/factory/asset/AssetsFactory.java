package com.example.csvccdshustbe.factory.asset;

import com.example.csvccdshustbe.dto.asset.CreateAssetDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.request.asset.CreateAssetRequest;

import java.util.Map;


public interface AssetsFactory {
    Asset createAsset(Map<String,Object> setAssetRequest);
}
