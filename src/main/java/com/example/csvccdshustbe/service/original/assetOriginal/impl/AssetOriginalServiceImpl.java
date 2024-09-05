package com.example.csvccdshustbe.service.original.assetOriginal.impl;

import com.example.csvccdshustbe.entity.AssetOriginal;
import com.example.csvccdshustbe.repository.assetOriginal.AssetOriginalRepository;
import com.example.csvccdshustbe.service.original.assetOriginal.AssetOriginalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetOriginalServiceImpl implements AssetOriginalService {

    @Autowired
    AssetOriginalRepository assetOriginalRepository;

    @Override
    public AssetOriginal save(AssetOriginal assetOriginal) {
        return assetOriginalRepository.save(assetOriginal);
    }
}
