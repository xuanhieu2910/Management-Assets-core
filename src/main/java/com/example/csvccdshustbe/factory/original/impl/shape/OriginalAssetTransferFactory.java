package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.ShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class OriginalAssetTransferFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        ShapeOriginalAssetTransfer assetTransfer = new ShapeOriginalAssetTransfer();
        assetTransfer.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        assetTransfer.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        assetTransfer.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        assetTransfer.setValueRecallWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueRecallWork")));
        assetTransfer.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        assetTransfer.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetTransfer.setTimeCreated(timeCurrent);
        assetTransfer.setTimeModified(timeCurrent);
        return assetTransfer;
    }
}
