package com.example.csvccdshustbe.service.original;

import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
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
import java.util.Optional;

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

    public BluePrintOriginalDto findBluePrintAssetOriginalByIdAsset(Integer idAsset) {
        return assetOriginalService.findBluePrintAssetOriginalByIdAsset(idAsset);
    }

    public void deleteAssetOriginal(String typeOriginal, Integer idInstance, Integer idOriginal) throws ValidateFiledException {
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                 originalAssetBuyService.deleteShapeOriginalAssetById(idInstance);
            }
            case ShapeOriginalAssetConnectActor -> {
                 originalAssetConnectActorService.deleteShapeOriginalAssetConnectActorById(idInstance);
            }
            case ShapeOriginalAssetConnectWoActor -> {
                 originalAssetConnectWoActorService.deleteShapeOriginalAssetConnectWoById(idInstance);
            }
            case ShapeOriginalAssetEvaluate -> {
                 originalAssetEvaluateService.deleteShapeOriginalAssetEvaluateById(idInstance);
            }
            case ShapeOriginalAssetGift -> {
                 originalAssetGiftService.deleteShapeOriginalAssetGiftById(idInstance);
            }
            case ShapeOriginalAssetInvest -> {
                 originalAssetInvestService.deleteShapeOriginalAssetInvestById(idInstance);
            }
            case ShapeOriginalAssetTransfer -> {
                 originalAssetTransferService.deleteShapeOriginalAssetTransfer(idInstance);
            }
            case NoShapeOriginalAssetGift -> {
                 noOriginalAssetGiftService.deleteNoShapeOriginalAssetGiftById(idInstance);
            }
            case NoShapeOriginalAssetBuy -> {
                 noOriginalAssetBuyService.deleteNoShapeOriginalAssetById(idInstance);
            }
            case NoShapeOriginalAssetUseLand -> {
                 noOriginalAssetUseLandService.deleteNoShapeOriginalAssetUseLandById(idInstance);
            }
            case NoShapeOriginalAssetEvaluate -> {
                 noOriginalAssetEvaluateService.deleteNoShapeOriginalAssetEvaluateById(idInstance);
            }
            case NoShapeOriginalAssetTransfer -> {
                 noOriginalAssetTransferService.deleteNoShapeOriginalAssetTransferById(idInstance);
            }
            case NoShapeOriginalAssetRentLand ->  {
                 noOriginalAssetRentLandService.deleteNoShapeOriginalAssetRendLandById(idInstance);
            }
            case NoShapeOriginalAssetTransferLand -> {
                 noOriginalAssetTransferLandService.deleteNoShapOriginalAssetTransferLandById(idInstance);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type original!");
            }
        }
        assetOriginalService.deleteAssetOriginalByIdOriginalAndIdInstance(idOriginal,idInstance);
    }


    public IOriginal findIOriginalByTypeOriginalAndIdInstance(String typeOriginal, Integer idInstance) throws ValidateFiledException {
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                return originalAssetBuyService.findShapeOriginalAssetBuyById(idInstance);
            }
            case ShapeOriginalAssetConnectActor -> {
                return originalAssetConnectActorService.findShapeOriginalAssetConnectActorById(idInstance);
            }
            case ShapeOriginalAssetConnectWoActor -> {
                return originalAssetConnectWoActorService.findShapeOriginalAssetConnecWoActorById(idInstance);
            }
            case ShapeOriginalAssetEvaluate -> {
                return originalAssetEvaluateService.findShapeOriginalAssetEvaluateById(idInstance);
            }
            case ShapeOriginalAssetGift -> {
                return originalAssetGiftService.findShapeOriginalAssetGiftById(idInstance);
            }
            case ShapeOriginalAssetInvest -> {
                return originalAssetInvestService.findShapeOriginalAssetInvestById(idInstance);
            }
            case ShapeOriginalAssetTransfer -> {
                return originalAssetTransferService.findShapeOriginalAssetTransferById(idInstance);
            }
            case NoShapeOriginalAssetGift -> {
                return noOriginalAssetGiftService.findNoShapeOriginalAssetGiftById(idInstance);
            }
            case NoShapeOriginalAssetBuy -> {
                return noOriginalAssetBuyService.findNoShapeOriginalAssetBuyById(idInstance);
            }
            case NoShapeOriginalAssetUseLand -> {
                return noOriginalAssetUseLandService.findNoShapeOriginalAssetUseLandById(idInstance);
            }
            case NoShapeOriginalAssetEvaluate -> {
                return noOriginalAssetEvaluateService.findNoShapeOriginalAssetEvaluateById(idInstance);
            }
            case NoShapeOriginalAssetTransfer -> {
                return noOriginalAssetTransferService.findNoShapeOriginalAssetTransferById(idInstance);
            }
            case NoShapeOriginalAssetRentLand ->  {
                return noOriginalAssetRentLandService.findNoShapeOriginalAssetRentLandById(idInstance);
            }
            case NoShapeOriginalAssetTransferLand -> {
                return noOriginalAssetTransferLandService.findNoShapeOriginalAssetTransferLandById(idInstance);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type original!");
            }
        }
    }

    public <T> IOriginal update(String typeOriginal, T dataOriginal) throws ValidateFiledException {
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                return originalAssetBuyService.save((ShapeOriginalAssetBuy) dataOriginal);
            }
            case ShapeOriginalAssetConnectActor -> {
                return originalAssetConnectActorService.save((ShapeOriginalAssetConnectActor) dataOriginal);
            }
            case ShapeOriginalAssetConnectWoActor -> {
                return originalAssetConnectWoActorService.save((ShapeOriginalAssetConnectWoActor) dataOriginal);
            }
            case ShapeOriginalAssetEvaluate -> {
                return originalAssetEvaluateService.save((ShapeOriginalAssetEvaluate) dataOriginal);
            }
            case ShapeOriginalAssetGift -> {
                return originalAssetGiftService.save((ShapeOriginalAssetGift) dataOriginal);
            }
            case ShapeOriginalAssetInvest -> {
                return originalAssetInvestService.save((ShapeOriginalAssetInvest) dataOriginal);
            }
            case ShapeOriginalAssetTransfer -> {
                return originalAssetTransferService.save((ShapeOriginalAssetTransfer) dataOriginal);
            }
            case NoShapeOriginalAssetGift -> {
                return noOriginalAssetGiftService.save((NoShapeOriginalAssetGift) dataOriginal);
            }
            case NoShapeOriginalAssetBuy -> {
                return noOriginalAssetBuyService.save((NoShapeOriginalAssetBuy) dataOriginal);
            }
            case NoShapeOriginalAssetUseLand -> {
                return noOriginalAssetUseLandService.save((NoShapeOriginalAssetUseLand) dataOriginal);
            }
            case NoShapeOriginalAssetEvaluate -> {
                return noOriginalAssetEvaluateService.save((NoShapeOriginalAssetEvaluate) dataOriginal);
            }
            case NoShapeOriginalAssetTransfer -> {
                return noOriginalAssetTransferService.save((NoShapeOriginalAssetTransfer) dataOriginal);
            }
            case NoShapeOriginalAssetRentLand ->  {
                return noOriginalAssetRentLandService.save((NoShapeOriginalAssetRentLand) dataOriginal);
            }
            case NoShapeOriginalAssetTransferLand -> {
                return noOriginalAssetTransferLandService.save((NoShapeOriginalAssetTransferLand) dataOriginal);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type original!");
            }
        }
    }
}
