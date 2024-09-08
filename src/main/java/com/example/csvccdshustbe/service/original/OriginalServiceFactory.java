package com.example.csvccdshustbe.service.original;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumOriginalFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.original.impl.shape.*;
import com.example.csvccdshustbe.service.original.assetOriginal.AssetOriginalService;
import com.example.csvccdshustbe.service.original.shape.assetBuy.OriginalAssetBuyService;
import com.example.csvccdshustbe.service.original.shape.assetConnectActor.OriginalAssetConnectActorService;
import com.example.csvccdshustbe.service.original.shape.assetConnectWoActor.OriginalAssetConnectWoActorService;
import com.example.csvccdshustbe.service.original.shape.assetEvaluate.OriginalAssetEvaluateService;
import com.example.csvccdshustbe.service.original.shape.assetGift.OriginalAssetGiftService;
import com.example.csvccdshustbe.service.original.shape.assetInvest.OriginalAssetInvestService;
import com.example.csvccdshustbe.service.original.shape.assetTransfer.OriginalAssetTransferService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class OriginalServiceFactory {

    @Autowired
    OriginalAssetBuyService originalAssetBuyService;
    @Autowired
    OriginalAssetConnectActorService originalAssetConnectActorService;
    @Autowired
    OriginalAssetConnectWoActorService originalAssetConnectWoActorService;
    @Autowired
    OriginalAssetEvaluateService originalAssetEvaluateService;
    @Autowired
    OriginalAssetGiftService originalAssetGiftService;
    @Autowired
    OriginalAssetInvestService originalAssetInvestService;
    @Autowired
    OriginalAssetTransferService originalAssetTransferService;
    @Autowired
    AssetOriginalService assetOriginalService;

    public void save(IOriginal original, Map<String,Object> originalDataAsset) throws ValidateFiledException {
        String typeOriginal = ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET));
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        Integer idInstance;
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                idInstance =  originalAssetBuyService.save((ShapeOriginalAssetBuy) original).getIdShapeOriginalAssetBuy();
            }
            case ShapeOriginalAssetConnectActor -> {
                idInstance = originalAssetConnectActorService.save((ShapeOriginalAssetConnectActor) original).getIdShapeOriginalAssetConnectActor();
            }
            case ShapeOriginalAssetConnectWoActor -> {
                idInstance =  originalAssetConnectWoActorService.save((ShapeOriginalAssetConnectWoActor) original).getIdShapeOriginalAssetConnectWoActor();
            }
            case ShapeOriginalAssetEvaluate -> {
                idInstance = originalAssetEvaluateService.save((ShapeOriginalAssetEvaluate) original).getIdShapeOriginalAssetEvaluate();
            }
            case ShapeOriginalAssetGift -> {
                idInstance = originalAssetGiftService.save((ShapeOriginalAssetGift) original).getIdShapeOriginalAssetGift();
            }
            case ShapeOriginalAssetInvest -> {
                idInstance = originalAssetInvestService.save((ShapeOriginalAssetInvest) original).getIdShapeOriginalAssetInvest();
            }
            case ShapeOriginalAssetTransfer -> {
                idInstance = originalAssetTransferService.save((ShapeOriginalAssetTransfer) original).getIdShapeOriginalAssetTransfer();
            }
            default -> {
                throw new ValidateFiledException("Don't exits type original!");
            }
        }
        assetOriginalService.save(createAssetOriginal(originalDataAsset, idInstance));
    }

    private AssetOriginal createAssetOriginal(Map<String, Object> originalDataAsset, Integer idInstance) {
        AssetOriginal original = new AssetOriginal();
        original.setIdAsset(ValueUtil.getIntegerByObject(originalDataAsset.get("idAsset")));
        original.setIdOriginal(ValueUtil.getIntegerByObject(originalDataAsset.get("idOriginal")));
        original.setIdInstance(idInstance);
        String timeCurrent = String.valueOf(new Date().getTime());
        original.setTimeCreated(timeCurrent);
        original.setTimeModified(timeCurrent);
        return original;
    }

    public void validateDataOriginal(Map<String,Object> dataOriginal) {
    }
}
