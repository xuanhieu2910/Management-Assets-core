package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class OriginalAssetConnectActorFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetConnectActor connectActor = new ShapeOriginalAssetConnectActor();
        connectActor.setIdOriginal(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idOriginal")));
        connectActor.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        return connectActor;
    }
}
