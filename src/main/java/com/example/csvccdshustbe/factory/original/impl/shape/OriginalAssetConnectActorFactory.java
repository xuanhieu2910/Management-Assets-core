package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectActor;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetConnectActorFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetConnectActor connectActor = new ShapeOriginalAssetConnectActor();
        connectActor.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeCreated(timeCurrent);
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetConnectActor connectActor = (ShapeOriginalAssetConnectActor) iOriginalDetails;
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }
}
