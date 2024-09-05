package com.example.csvccdshustbe.service.declare.assetDeclare.impl;

import com.example.csvccdshustbe.entity.AssetDeclare;
import com.example.csvccdshustbe.repository.assetDeclare.AssetDeclareRepository;
import com.example.csvccdshustbe.service.declare.assetDeclare.AssetDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetDeclareServiceImpl implements AssetDeclareService {

    @Autowired
    AssetDeclareRepository assetDeclareRepository;
    @Override
    public AssetDeclare save(AssetDeclare declare) {
        return assetDeclareRepository.save(declare);
    }
}
