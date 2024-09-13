package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransfer;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoOriginalAssetTransferFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetTransfer transfer = new NoShapeOriginalAssetTransfer();
        transfer.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        transfer.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        transfer.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        transfer.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        transfer.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        transfer.setTimeCreated(timeCurrent);
        transfer.setTimeModified(timeCurrent);
        return transfer;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        NoShapeOriginalAssetTransfer transfer = (NoShapeOriginalAssetTransfer) iOriginalDetails;
        transfer.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        transfer.setValueWork(ValueUtil.getDoubleByObject(originalDataAsset.get("valueWork")));
        transfer.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        transfer.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        transfer.setTimeModified(timeCurrent);
        return transfer;
    }
}
