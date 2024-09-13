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

    @Override
    public IOriginal updateModule(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        NoShapeOriginalAssetUseLand useLand = (NoShapeOriginalAssetUseLand) iOriginalDetails;
        useLand.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        useLand.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        useLand.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        useLand.setTimeModified(timeCurrent);
        return useLand;
    }
}
