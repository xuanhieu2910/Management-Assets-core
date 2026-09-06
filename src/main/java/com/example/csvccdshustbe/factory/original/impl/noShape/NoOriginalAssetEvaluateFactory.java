package com.example.csvccdshustbe.factory.original.impl.noShape;

import com.example.csvccdshustbe.dto.original.AssetOriginalDto;
import com.example.csvccdshustbe.entity.IOriginal;
import com.example.csvccdshustbe.entity.NoShapeOriginalAssetEvaluate;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class NoOriginalAssetEvaluateFactory implements OriginalFactory {
    @Override
    public IOriginal createOriginal(Map<String, Object> mapOriginalCreate) {
        NoShapeOriginalAssetEvaluate evaluate = new NoShapeOriginalAssetEvaluate();
        evaluate.setIdAsset(ValueUtil.getIntegerByObject(mapOriginalCreate.get("idAsset")));
        evaluate.setValueBuy(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueBuy")));
        evaluate.setValueTax(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueTax")));
        evaluate.setValueOther(ValueUtil.getDoubleByObject(mapOriginalCreate.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        evaluate.setTimeCreated(timeCurrent);
        evaluate.setTimeModified(timeCurrent);
        return evaluate;
    }

    @Override
    public IOriginal copyOriginal(AssetOriginalDto assetOriginalDto, Integer idAsset) {
        NoShapeOriginalAssetEvaluate evaluateRoot = (NoShapeOriginalAssetEvaluate) assetOriginalDto.getDataDetails();
        NoShapeOriginalAssetEvaluate evaluate = new NoShapeOriginalAssetEvaluate();
        evaluate.setIdAsset(idAsset);
        evaluate.setValueBuy(evaluateRoot.getValueBuy());
        evaluate.setValueTax(evaluateRoot.getValueTax());
        evaluate.setValueOther(evaluateRoot.getValueOther());
        String timeCurrent = String.valueOf(new Date().getTime());
        evaluate.setTimeCreated(timeCurrent);
        evaluate.setTimeModified(timeCurrent);
        return evaluate;
    }

    @Override
    public IOriginal updateOriginal(Map<String, Object> originalDataAsset, IOriginal iOriginalDetails) {
        NoShapeOriginalAssetEvaluate evaluate = (NoShapeOriginalAssetEvaluate) iOriginalDetails;
        evaluate.setValueBuy(ValueUtil.getDoubleByObject(originalDataAsset.get("valueBuy")));
        evaluate.setValueTax(ValueUtil.getDoubleByObject(originalDataAsset.get("valueTax")));
        evaluate.setValueOther(ValueUtil.getDoubleByObject(originalDataAsset.get("valueOther")));
        String timeCurrent = String.valueOf(new Date().getTime());
        evaluate.setTimeModified(timeCurrent);
        return evaluate;
    }
}
