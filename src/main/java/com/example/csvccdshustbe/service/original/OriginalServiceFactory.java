package com.example.csvccdshustbe.service.original;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumOriginalFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.original.impl.shape.*;
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

    public IOriginal save(IOriginal original, Map<String,Object> originalDataAsset) throws ValidateFiledException {
        String typeOriginal = ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET));
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                return originalAssetBuyService.save((ShapeOriginalAssetBuy) original);
            }
            case ShapeOriginalAssetConnectActor -> {
                return originalAssetConnectActorService.save((ShapeOriginalAssetConnectActor) original);
            }
            case ShapeOriginalAssetConnectWoActor -> {
                return originalAssetConnectWoActorService.save((ShapeOriginalAssetConnectWoActor) original);
            }
            case ShapeOriginalAssetEvaluate -> {
                return originalAssetEvaluateService.save((ShapeOriginalAssetEvaluate) original);
            }
            case ShapeOriginalAssetGift -> {
                return originalAssetGiftService.save((ShapeOriginalAssetGift) original);
            }
            case ShapeOriginalAssetInvest -> {
                return originalAssetInvestService.save((ShapeOriginalAssetInvest) original);
            }
            case ShapeOriginalAssetTransfer -> {
                return originalAssetTransferService.save((ShapeOriginalAssetTransfer) original);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type original!");
            }
        }
    }

}
