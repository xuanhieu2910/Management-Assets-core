package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoShapeAssetRentLandFactory  implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetRentLand rentLand = new NoShapeOriginalAssetRentLand();
        rentLand.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        rentLand.setValueRent(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueRent")));
        rentLand.setValueWork(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueWork")));
        rentLand.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        rentLand.setTimeCreated(timeCurrent);
        rentLand.setTimeModified(timeCurrent);
        return rentLand;
    }
}
