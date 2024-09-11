package com.example.csvccdshustbe.service.original;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumOriginalFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.original.impl.shape.*;
import com.example.csvccdshustbe.service.original.assetOriginal.AssetOriginalService;
import com.example.csvccdshustbe.service.original.noShape.assetBuy.NoOriginalAssetBuyService;
import com.example.csvccdshustbe.service.original.noShape.assetEvaluate.NoOriginalAssetEvaluateService;
import com.example.csvccdshustbe.service.original.noShape.assetGift.NoOriginalAssetGiftService;
import com.example.csvccdshustbe.service.original.noShape.assetTranfer.NoOriginalAssetTransferService;
import com.example.csvccdshustbe.service.original.noShape.assetUseLand.NoOriginalAssetUseLandService;
import com.example.csvccdshustbe.service.original.noShape.rentLand.NoOriginalAssetRentLandService;
import com.example.csvccdshustbe.service.original.noShape.transferLand.NoOriginalAssetTransferLandService;
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
    @Autowired
    NoOriginalAssetBuyService noOriginalAssetBuyService;
    @Autowired
    NoOriginalAssetEvaluateService noOriginalAssetEvaluateService;
    @Autowired
    NoOriginalAssetGiftService noOriginalAssetGiftService;
    @Autowired
    NoOriginalAssetTransferService noOriginalAssetTransferService;
    @Autowired
    NoOriginalAssetUseLandService noOriginalAssetUseLandService;
    @Autowired
    NoOriginalAssetRentLandService noOriginalAssetRentLandService;
    @Autowired
    NoOriginalAssetTransferLandService noOriginalAssetTransferLandService;
    @Autowired
    OriginalService originalService;

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
            case NoShapeOriginalAssetGift -> {
                idInstance = noOriginalAssetGiftService.save((NoShapeOriginalAssetGift) original).getIdNoShapeOriginalAssetGift();
            }
            case NoShapeOriginalAssetBuy -> {
                idInstance = noOriginalAssetBuyService.save((NoShapeOriginalAssetBuy) original).getIdNoShapeOriginalAssetBuy();
            }
            case NoShapeOriginalAssetUseLand -> {
                idInstance = noOriginalAssetUseLandService.save((NoShapeOriginalAssetUseLand) original).getIdNoShapeOriginalAssetUseLand();
            }
            case NoShapeOriginalAssetEvaluate -> {
                idInstance = noOriginalAssetEvaluateService.save((NoShapeOriginalAssetEvaluate) original).getIdNoShapeOriginalAssetEvaluate();
            }
            case NoShapeOriginalAssetTransfer -> {
                idInstance = noOriginalAssetTransferService.save((NoShapeOriginalAssetTransfer) original).getIdNoShapeOriginalAssetTransfer();
            }
            case NoShapeOriginalAssetRentLand ->  {
                idInstance = noOriginalAssetRentLandService.save((NoShapeOriginalAssetRentLand) original).getIdNoShapeOriginalAssetRentLand();
            }
            case NoShapeOriginalAssetTransferLand -> {
                idInstance = noOriginalAssetTransferLandService.save((NoShapeOriginalAssetTransferLand) original).getIdNoShapeOriginalAssetTransferLand();
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
        String keyTypeOriginal = ValueUtil.getStringByObject(dataOriginal.get(Constants.KEY_TYPE_ORIGINAL_ASSET));
        Original original = originalService.findOriginalByHardCodeAndStatus(keyTypeOriginal, Constants.ORIGINALS_VISIBLE);
        dataOriginal.put("idOriginal", original.getIdOriginal());
    }

    public Map<String, Object> findDataDetailByTypeOriginalAndIdInstance(String typeOriginal, Integer idInstance) throws ValidateFiledException, IllegalAccessException {
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                return originalAssetBuyService.findOriginalAssetBuyId(idInstance);
            }
            case ShapeOriginalAssetConnectActor -> {
                return originalAssetConnectActorService.findOriginalConnectActorById(idInstance);
            }
            case ShapeOriginalAssetConnectWoActor -> {
                return originalAssetConnectWoActorService.findOriginalConnectWoActorById(idInstance);
            }
            case ShapeOriginalAssetEvaluate -> {
                return originalAssetEvaluateService.findOriginalEvaluateById(idInstance);
            }
            case ShapeOriginalAssetGift -> {
                return originalAssetGiftService.findOriginalAssetGiftById(idInstance);
            }
            case ShapeOriginalAssetInvest -> {
                return originalAssetInvestService.findOriginalAssetInvestById(idInstance);
            }
            case ShapeOriginalAssetTransfer -> {
                return originalAssetTransferService.findOriginalAssetTransferById(idInstance);
            }
            case NoShapeOriginalAssetGift -> {
                return noOriginalAssetGiftService.findNoOriginalAssetGiftById(idInstance);
            }
            case NoShapeOriginalAssetBuy -> {
                return noOriginalAssetBuyService.findNoOriginalAssetBuyId(idInstance);
            }
            case NoShapeOriginalAssetUseLand -> {
                return noOriginalAssetUseLandService.findNoOriginalAssetUseLandById(idInstance);
            }
            case NoShapeOriginalAssetEvaluate -> {
                return noOriginalAssetEvaluateService.findNoOriginalAssetEvaluateById(idInstance);
            }
            case NoShapeOriginalAssetTransfer -> {
                return noOriginalAssetTransferService.findNoOriginalAssetTransferById(idInstance);
            }
            case NoShapeOriginalAssetRentLand ->  {
                return noOriginalAssetRentLandService.findNoOriginalAssetRentLandById(idInstance);
            }
            case NoShapeOriginalAssetTransferLand -> {
                return noOriginalAssetTransferLandService.findNoOriginalAssetTransferLandById(idInstance);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type original!");
            }
        }
    }
}
