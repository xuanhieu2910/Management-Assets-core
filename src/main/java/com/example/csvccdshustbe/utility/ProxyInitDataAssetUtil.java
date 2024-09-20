package com.example.csvccdshustbe.utility;

import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumDeclareFactory;
import com.example.csvccdshustbe.enums.EnumModuleFactory;
import com.example.csvccdshustbe.enums.EnumOriginalFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.declare.impl.CommonDeclareFactory;
import com.example.csvccdshustbe.factory.declare.impl.HouseDeclareFactory;
import com.example.csvccdshustbe.factory.module.impl.*;
import com.example.csvccdshustbe.factory.original.impl.noShape.*;
import com.example.csvccdshustbe.factory.original.impl.shape.*;


public class ProxyInitDataAssetUtil {


    public static Object proxyInitModuleDataAsset(String typeModule) throws ValidateFiledException {
        EnumModuleFactory enumModuleFactory = Enum.valueOf(EnumModuleFactory.class, typeModule);
        switch (enumModuleFactory) {
            case MedicineModule -> {
                return new MedicineModuleFactory();
            }
            case MachineModule -> {
                return new MachineModuleFactory();
            }
            case HouseModule -> {
                return new HouseModuleFactory();
            }
            case GroundModule -> {
                return new GroundModuleFactory();
            }
            case CarModule -> {
                return new CarModuleFactory();
            }
            case TreeAndAnimalModule -> {
                return new TreeAndAnimalModuleFactory();
            }
            case ArchitectureModule -> {
                return new ArchitectureModuleFactory();
            }
            case OtherAssetModule -> {
                return new OtherAssetModuleFactory();
            }
            case OtherVehicleTransportModule -> {
                return new OtherVehicleTransportModuleFactory();
            }
            default -> {
                throw new ValidateFiledException("Don't exits type modules!");
            }
        }
    }

    public static Object proxyInitDeclareDataAsset(String typeDeclare) throws ValidateFiledException {
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                return new HouseDeclareFactory();
            }
            case GroundDeclare -> {
                return new GroundDeclare();
            }
            case CommonDeclare -> {
                return new CommonDeclareFactory();
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
    }

    public static Object proxyInitOriginalDataAsset(String typeOriginal) throws ValidateFiledException {
        EnumOriginalFactory enumDeclareFactory = Enum.valueOf(EnumOriginalFactory.class, typeOriginal);
        switch (enumDeclareFactory){
            case ShapeOriginalAssetBuy -> {
                return new OriginalAssetBuyFactory();
            }
            case ShapeOriginalAssetConnectActor -> {
                return new OriginalAssetConnectActorFactory();
            }
            case ShapeOriginalAssetConnectWoActor -> {
                return new OriginalAssetConnectWoActorFactory();
            }
            case ShapeOriginalAssetEvaluate -> {
                return new OriginalAssetEvaluateFactory();
            }
            case ShapeOriginalAssetGift -> {
                return new OriginalAssetGiftFactory();
            }
            case ShapeOriginalAssetInvest -> {
                return new OriginalAssetInvestFactory();
            }
            case ShapeOriginalAssetTransfer -> {
                return new OriginalAssetTransferFactory();
            }
            case NoShapeOriginalAssetBuy -> {
                return new NoOriginalAssetBuyFactory();
            }
            case NoShapeOriginalAssetGift -> {
                return new NoOriginalAssetGiftFactory();
            }
            case NoShapeOriginalAssetEvaluate -> {
                return new NoOriginalAssetEvaluateFactory();
            }
            case NoShapeOriginalAssetTransfer -> {
                return new NoOriginalAssetTransferFactory();
            }
            case NoShapeOriginalAssetUseLand -> {
                return new NoOriginalAssetUseLandFactory();
            }
            case NoShapeOriginalAssetRentLand -> {
                return new NoShapeOriginalAssetRentLandFactory();
            }
            case NoShapeOriginalAssetTransferLand -> {
                return new NoShapeOriginalAssetTransferLandFactory();
            }
            default -> {
                throw new ValidateFiledException("Don't exits original");
            }
        }
    }
}
