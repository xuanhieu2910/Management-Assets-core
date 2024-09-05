package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetGift;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class OriginalAssetGiftFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetGift assetGift = new ShapeOriginalAssetGift();
        assetGift.setIdOriginal(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idOriginal")));
        assetGift.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        assetGift.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        assetGift.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        assetGift.setValueRecallWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueRecallWork")));
        assetGift.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        assetGift.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        return assetGift;
    }
}
