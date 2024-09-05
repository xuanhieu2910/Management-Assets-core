package com.example.csvccdshustbe.service.original.shape.assetConnectWoActor.impl;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;
import com.example.csvccdshustbe.repository.shapeOriginalAssetConnectWoActor.ShapeOriginalAssetConnectWoActorRepository;
import com.example.csvccdshustbe.service.original.shape.assetConnectWoActor.OriginalAssetConnectWoActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OriginalAssetConnectWoActorServiceImpl implements OriginalAssetConnectWoActorService {

    @Autowired
    ShapeOriginalAssetConnectWoActorRepository shapeOriginalAssetConnectWoActorRepository;

    @Override
    public ShapeOriginalAssetConnectWoActor save(ShapeOriginalAssetConnectWoActor connectWoActor) {
        return shapeOriginalAssetConnectWoActorRepository.save(connectWoActor);
    }
}
