package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetTransferLand;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoShapeOriginalAssetTransferLandFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetTransferLand transferLand = new NoShapeOriginalAssetTransferLand();
        transferLand.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        transferLand.setValueUse(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueUse")));
        transferLand.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        transferLand.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        transferLand.setTimeCreated(timeCurrent);
        transferLand.setTimeModified(timeCurrent);
        return transferLand;
    }

    @Override
    public IOriginal updateModule(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        NoShapeOriginalAssetTransferLand transferLand = (NoShapeOriginalAssetTransferLand) iOriginalDetails;
        transferLand.setValueUse(ValueUtil.getDoubleByObject(originalDataAsset.get("valueUse")));
        transferLand.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        transferLand.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        transferLand.setTimeModified(timeCurrent);
        return transferLand;
    }
}
