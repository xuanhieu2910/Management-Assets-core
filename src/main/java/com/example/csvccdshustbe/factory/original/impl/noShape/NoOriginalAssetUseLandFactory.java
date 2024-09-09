package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetUseLand;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoOriginalAssetUseLandFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetUseLand useLand = new NoShapeOriginalAssetUseLand();
        useLand.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        useLand.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        useLand.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        useLand.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        useLand.setTimeCreated(timeCurrent);
        useLand.setTimeModified(timeCurrent);
        return useLand;
    }
}
