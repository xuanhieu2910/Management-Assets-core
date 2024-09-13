package com.example.csvccdshustbe.service.original.shape.assetConnectActor;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;

import java.util.Map;

public interface OriginalAssetConnectActorService {

    ShapeOriginalAssetConnectActor save(ShapeOriginalAssetConnectActor shapeOriginalAssetConnectActor);

    Map<String, Object> findOriginalConnectActorById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetConnectActorById(Integer idInstance);

    ShapeOriginalAssetConnectActor findShapeOriginalAssetConnectActorById(Integer idInstance);
}
