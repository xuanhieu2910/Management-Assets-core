package com.example.csvccdshustbe.service.levelTypeAsset;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.levelTypeAsset.CreateLevelTypeAssetRequest;
import com.example.csvccdshustbe.request.levelTypeAsset.UpdateLevelTypeAssetRequest;
import com.example.csvccdshustbe.response.levelTypeAsset.FindAllLevelTypeAssetResponse;

import java.util.List;

public interface LevelTypeAssetService {
    List<FindAllLevelTypeAssetResponse> findAllLevelTypeAssetResponseByStatus(Integer status);
    void createLevelTypeAsset(CreateLevelTypeAssetRequest request) throws ValidateFiledException;

    void updateLevelTypeAsset(UpdateLevelTypeAssetRequest request) throws ValidateFiledException;

    void deleteLevelTypeAssetsByIdLTA(Integer idLevelTypeAsset);
}
