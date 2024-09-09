package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetBuy;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoOriginalAssetBuyFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetBuy noShapeOriginalAssetBuy = new NoShapeOriginalAssetBuy();
        noShapeOriginalAssetBuy.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        noShapeOriginalAssetBuy.setIdMethodBuyAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idMethodBuyAsset")));
        noShapeOriginalAssetBuy.setIdTypeBuyAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idTypeBuyAsset")));
        noShapeOriginalAssetBuy.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        noShapeOriginalAssetBuy.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        noShapeOriginalAssetBuy.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        noShapeOriginalAssetBuy.setTimeCreated(timeCurrent);
        noShapeOriginalAssetBuy.setTimeModified(timeCurrent);
        return noShapeOriginalAssetBuy;
    }
}
