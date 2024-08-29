package com.example.csvccdshustbe.service.levelTypeAsset.impl;

import com.example.csvccdshustbe.entity.LevelTypeAsset;
import com.example.csvccdshustbe.repository.levelTypeAsset.LevelTypeAssetRepository;
import com.example.csvccdshustbe.service.levelTypeAsset.LevelTypeAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LevelTypeAssetServiceImpl implements LevelTypeAssetService {

    @Autowired
    LevelTypeAssetRepository levelTypeAssetRepository;
    @Override
    public List<LevelTypeAsset>findAllLevelTypeAsset(){
        return levelTypeAssetRepository.findAllLevelTypeAsset();
    }

}
