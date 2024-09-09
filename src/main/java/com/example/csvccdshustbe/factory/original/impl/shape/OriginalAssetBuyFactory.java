package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetBuy;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetBuyFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetBuy shapeOriginalAssetBuy = new ShapeOriginalAssetBuy();
        shapeOriginalAssetBuy.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        shapeOriginalAssetBuy.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        shapeOriginalAssetBuy.setValueDiscount(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueDiscount")));
        shapeOriginalAssetBuy.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        shapeOriginalAssetBuy.setValueRecallWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueRecallWork")));
        shapeOriginalAssetBuy.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        shapeOriginalAssetBuy.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        shapeOriginalAssetBuy.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idMethodBuyAsset")));
        shapeOriginalAssetBuy.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idTypeBuyAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        shapeOriginalAssetBuy.setTimeCreated(timeCurrent);
        shapeOriginalAssetBuy.setTimeModified(timeCurrent);
        return shapeOriginalAssetBuy;
    }
}
