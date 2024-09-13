package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetEvaluateFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetEvaluate assetEvaluate = new ShapeOriginalAssetEvaluate();
        assetEvaluate.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        assetEvaluate.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        assetEvaluate.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        assetEvaluate.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetEvaluate.setTimeCreated(timeCurrent);
        assetEvaluate.setTimeModified(timeCurrent);
        return assetEvaluate;
    }

    @Override
    public IOriginal updateModule(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetEvaluate assetEvaluate = (ShapeOriginalAssetEvaluate) iOriginalDetails;
        assetEvaluate.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        assetEvaluate.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        assetEvaluate.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetEvaluate.setTimeModified(timeCurrent);
        return assetEvaluate;
    }
}
