package com.example.csvccdshustbe.service.original.shape.assetConnectWoActor;

import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;

import java.util.Map;

public interface OriginalAssetConnectWoActorService {

    ShapeOriginalAssetConnectWoActor save(ShapeOriginalAssetConnectWoActor connectWoActor);

    Map<String, Object> findOriginalConnectWoActorById(Integer idInstance) throws IllegalAccessException;
}
