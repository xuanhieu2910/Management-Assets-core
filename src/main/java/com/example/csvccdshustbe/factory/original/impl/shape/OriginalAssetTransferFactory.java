package com.example.csvccdshustbe.factory.original.impl.shape;

import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
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

    @Override
    public IOriginal copyOriginal(AssetOriginalDto assetOriginalDto, Integer idAsset) {
        ShapeOriginalAssetTransfer assetTransferRoot = new ShapeOriginalAssetTransfer();
        ShapeOriginalAssetTransfer assetTransfer = new ShapeOriginalAssetTransfer();
        assetTransfer.setIdAsset(idAsset);
        assetTransfer.setValueBuy(assetTransferRoot.getValueBuy());
        assetTransfer.setValueWork(assetTransferRoot.getValueWork());
        assetTransfer.setValueRecallWork(assetTransferRoot.getValueRecallWork());
        assetTransfer.setValueTax(assetTransferRoot.getValueTax());
        assetTransfer.setValueOther(assetTransferRoot.getValueOther());
        String timeCurrent = String.valueOf(new Date().getTime());
        assetTransfer.setTimeCreated(timeCurrent);
        assetTransfer.setTimeModified(timeCurrent);
        return assetTransfer;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        ShapeOriginalAssetTransfer assetTransfer = (ShapeOriginalAssetTransfer) iOriginalDetails;
        assetTransfer.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        assetTransfer.setValueWork(ValueUtil.getDoubleByObject(originalDataAsset.get("valueWork")));
        assetTransfer.setValueRecallWork(ValueUtil.getDoubleByObject(originalDataAsset.get("valueRecallWork")));
        assetTransfer.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        assetTransfer.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        assetTransfer.setTimeModified(timeCurrent);
        return assetTransfer;
    }
}
