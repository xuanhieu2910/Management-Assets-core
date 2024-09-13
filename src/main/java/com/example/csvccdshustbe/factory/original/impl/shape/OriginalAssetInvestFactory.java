package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetInvest;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetInvestFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetInvest assetInvest = new ShapeOriginalAssetInvest();
        assetInvest.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        assetInvest.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetInvest.setTimeCreated(timeCurrent);
        assetInvest.setTimeModified(timeCurrent);
        return assetInvest;
    }

    @Override
    public IOriginal updateModule(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetInvest assetInvest = (ShapeOriginalAssetInvest) iOriginalDetails;
        assetInvest.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetInvest.setTimeModified(timeCurrent);
        return assetInvest;
    }
}
