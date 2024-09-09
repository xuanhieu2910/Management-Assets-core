package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetGift;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoOriginalAssetGiftFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetGift gift = new NoShapeOriginalAssetGift();
        gift.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        gift.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        gift.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        gift.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        gift.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        gift.setTimeCreated(timeCurrent);
        gift.setTimeModified(timeCurrent);
        return gift;
    }
}
