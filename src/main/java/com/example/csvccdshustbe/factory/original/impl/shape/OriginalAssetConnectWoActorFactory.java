package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetConnectWoActor;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class OriginalAssetConnectWoActorFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetConnectWoActor connectWoActor = new ShapeOriginalAssetConnectWoActor();
        connectWoActor.setIdOriginal(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idOriginal")));
        connectWoActor.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        connectWoActor.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        connectWoActor.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        connectWoActor.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        return connectWoActor;
    }
}
