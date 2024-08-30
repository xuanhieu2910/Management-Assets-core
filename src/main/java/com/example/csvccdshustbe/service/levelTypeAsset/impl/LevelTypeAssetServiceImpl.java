package com.example.csvccdshustbe.service.levelTypeAsset.impl;


import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.repository.levelTypeAsset.LevelTypeAssetRepository;

import com.example.csvccdshustbe.response.levelTypeAsset.FindAllLevelTypeAssetResponse;
import com.example.csvccdshustbe.service.levelTypeAsset.LevelTypeAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LevelTypeAssetServiceImpl implements LevelTypeAssetService {

    @Autowired
    LevelTypeAssetRepository levelTypeAssetRepository;
    @Override
    public List<FindAllLevelTypeAssetResponse> findAllLevelTypeAssetResponseByStatus(Integer status){
        return convertToFindAllLevelTypeAsset(levelTypeAssetRepository.findAllLevelTypeAssetByStatus(status));
    }

    private List<FindAllLevelTypeAssetResponse> convertToFindAllLevelTypeAsset(List<LevelTypeAsset> allLevelTypeAssetByStatus) {
        List<FindAllLevelTypeAssetResponse>responses = new ArrayList<>();
        for (LevelTypeAsset levelTypeAsset : allLevelTypeAssetByStatus){
            FindAllLevelTypeAssetResponse response = new FindAllLevelTypeAssetResponse();
            response.setIdLevelTypeAsset(levelTypeAsset.getIdLevelTypeAsset());
            response.setName(levelTypeAsset.getName());
            responses.add(response);
        }
        return responses;
    }

}
