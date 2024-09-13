package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetConnectWoActorFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetConnectWoActor connectWoActor = new ShapeOriginalAssetConnectWoActor();
        connectWoActor.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        connectWoActor.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        connectWoActor.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        connectWoActor.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        connectWoActor.setTimeCreated(timeCurrent);
        connectWoActor.setTimeModified(timeCurrent);
        return connectWoActor;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetConnectWoActor connectWoActor = (ShapeOriginalAssetConnectWoActor) iOriginalDetails;
        connectWoActor.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        connectWoActor.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        connectWoActor.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        connectWoActor.setTimeModified(timeCurrent);
        return connectWoActor;
    }
}
