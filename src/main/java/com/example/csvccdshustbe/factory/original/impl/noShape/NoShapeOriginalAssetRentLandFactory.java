package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetRentLand;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoShapeOriginalAssetRentLandFactory implements OriginalFactory {
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

    @Override
    public IOriginal copyOriginal(AssetOriginalDto assetOriginalDto, Integer idAsset) {
        NoShapeOriginalAssetRentLand rentLandRoot = (NoShapeOriginalAssetRentLand) assetOriginalDto.getDataDetails();
        NoShapeOriginalAssetRentLand rentLand = new NoShapeOriginalAssetRentLand();
        rentLand.setIdAsset(idAsset);
        rentLand.setValueRent(rentLandRoot.getValueRent());
        rentLand.setValueWork(rentLandRoot.getValueWork());
        rentLand.setValueOther(rentLandRoot.getValueOther());
        String timeCurrent = String.valueOf(new Date().getTime());
        rentLand.setTimeCreated(timeCurrent);
        rentLand.setTimeModified(timeCurrent);
        return rentLand;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        NoShapeOriginalAssetRentLand rentLand = (NoShapeOriginalAssetRentLand) iOriginalDetails;
        rentLand.setValueRent(ValueUtil.getDoubleByObject(originalDataAsset.get("valueRent")));
        rentLand.setValueWork(ValueUtil.getDoubleByObject(originalDataAsset.get("valueWork")));
        rentLand.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        rentLand.setTimeModified(timeCurrent);
        return rentLand;
    }
}
