package com.example.csvccdshustbe.service.original.shape.assetConnectActor.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import com.example.csvccdshustbe.repository.shapeOriginalAssetConnectActor.ShapeOriginalAssetConnectActorRepository;
import com.example.csvccdshustbe.service.original.shape.assetConnectActor.OriginalAssetConnectActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetConnectActorServiceImpl implements OriginalAssetConnectActorService {

    @Autowired
    ShapeOriginalAssetConnectActorRepository shapeOriginalAssetConnectActorRepository;


    @Override
    public ShapeOriginalAssetConnectActor save(ShapeOriginalAssetConnectActor shapeOriginalAssetConnectActor) {
        return shapeOriginalAssetConnectActorRepository.save(shapeOriginalAssetConnectActor);
    }
}
