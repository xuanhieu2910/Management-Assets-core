package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetGiftFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetGift assetGift = new ShapeOriginalAssetGift();
        assetGift.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        assetGift.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        assetGift.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        assetGift.setValueRecallWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueRecallWork")));
        assetGift.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        assetGift.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetGift.setTimeCreated(timeCurrent);
        assetGift.setTimeModified(timeCurrent);
        return assetGift;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetGift assetGift = (ShapeOriginalAssetGift) iOriginalDetails;
        assetGift.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        assetGift.setValueWork(ValueUtil.getDoubleByObject(originalDataAsset.get("valueWork")));
        assetGift.setValueRecallWork(ValueUtil.getDoubleByObject(originalDataAsset.get("valueRecallWork")));
        assetGift.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        assetGift.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetGift.setTimeModified(timeCurrent);
        return assetGift;
    }
}
