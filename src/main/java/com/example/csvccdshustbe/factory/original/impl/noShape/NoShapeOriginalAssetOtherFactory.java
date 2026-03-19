package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetOther;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoShapeOriginalAssetOtherFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetOther connectActor = new NoShapeOriginalAssetOther();
        connectActor.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeCreated(timeCurrent);
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }

    @Override
    public IOriginal copyOriginal(AssetOriginalDto assetOriginalDto, Integer idAsset) {
        NoShapeOriginalAssetOther connectActorRoot = (NoShapeOriginalAssetOther) assetOriginalDto.getDataDetails();
        NoShapeOriginalAssetOther connectActor = new NoShapeOriginalAssetOther();
        connectActor.setIdAsset(idAsset);
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeCreated(timeCurrent);
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        NoShapeOriginalAssetOther connectActor = (NoShapeOriginalAssetOther) iOriginalDetails;
        String timeCurrent = String.valueOf(new Date().getTime());
        connectActor.setTimeModified(timeCurrent);
        return connectActor;
    }
}
