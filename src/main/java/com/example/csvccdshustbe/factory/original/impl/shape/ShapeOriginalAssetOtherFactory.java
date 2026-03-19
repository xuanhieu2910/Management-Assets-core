package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetOther;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class ShapeOriginalAssetOtherFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetOther connectActor = new ShapeOriginalAssetOther();
        connectActor.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeCreated(timeCurrent);
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }

    @Override
    public IOriginal copyOriginal(AssetOriginalDto assetOriginalDto, Integer idAsset) {
        ShapeOriginalAssetOther connectActorRoot = (ShapeOriginalAssetOther) assetOriginalDto.getDataDetails();
        ShapeOriginalAssetOther connectActor = new ShapeOriginalAssetOther();
        connectActor.setIdAsset(idAsset);
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeCreated(timeCurrent);
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetOther connectActor = (ShapeOriginalAssetOther) iOriginalDetails;
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }
}
