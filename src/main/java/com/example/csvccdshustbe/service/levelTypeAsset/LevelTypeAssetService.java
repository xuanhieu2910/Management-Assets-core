package com.example.csvccdshustbe.service.levelTypeAsset;

import com.example.csvccdshustbe.response.levelTypeAsset.FindAllLevelTypeAssetResponse;

import java.util.List;

public interface LevelTypeAssetService {
    List<FindAllLevelTypeAssetResponse> findAllLevelTypeAssetResponseByStatus(Integer status);
}
