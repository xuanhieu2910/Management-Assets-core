package com.example.csvccdshustbe.service.original.shape.assetConnectWoActor;

import com.example.csvccdshustbe.dto.original.shape.ShapeOriginalAssetConnectWoActorDetailsDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;

import java.util.Map;

public interface OriginalAssetConnectWoActorService {

    ShapeOriginalAssetConnectWoActor save(ShapeOriginalAssetConnectWoActor connectWoActor);

    ShapeOriginalAssetConnectWoActorDetailsDto findOriginalConnectWoActorById(Integer idInstance) throws IllegalAccessException;

    void deleteShapeOriginalAssetConnectWoById(Integer idInstance);

    ShapeOriginalAssetConnectWoActor findShapeOriginalAssetConnecWoActorById(Integer idInstance);
}
