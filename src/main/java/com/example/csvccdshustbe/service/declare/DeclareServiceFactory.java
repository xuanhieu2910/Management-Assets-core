package com.example.csvccdshustbe.service.declare;


import com.example.csvccdshustbe.dto.assetCurrentUsage.AssetCurrentUsageDetailsDto;
import com.example.csvccdshustbe.dto.declare.AssetDeclareDto;
import com.example.csvccdshustbe.dto.declare.BluePrintDeclareDto;
import com.example.csvccdshustbe.dto.declare.CommonDeclareDetailsDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.EnumDeclareFactory;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.service.assetCurrentUsage.AssetCurrentUsageService;
import com.example.csvccdshustbe.service.declare.assetDeclare.AssetDeclareService;
import com.example.csvccdshustbe.service.declare.commonDeclare.CommonDeclareService;
import com.example.csvccdshustbe.service.declare.groundDeclare.GroundDeclareService;
import com.example.csvccdshustbe.service.declare.houseDeclare.HouseDeclareService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class DeclareServiceFactory {


    @Autowired
    CommonDeclareService commonDeclareService;
    @Autowired
    HouseDeclareService houseDeclareService;
    @Autowired
    GroundDeclareService groundDeclareService;
    @Autowired
    AssetDeclareService assetDeclareService;
    @Autowired
    AssetCurrentUsageService assetCurrentUsageService;
    @Autowired
    DeclareService declareService;


    public void save(IDeclare declare, Map<String,Object> declareDataAsset) throws ValidateFiledException {
        String typeDeclare = ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE));
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        Integer idInstance;
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                idInstance = houseDeclareService.save((HouseDeclare) declare).getIdHouseDeclare();
            }
            case GroundDeclare -> {
                idInstance = groundDeclareService.save((GroundDeclare) declare).getIdGroundDeclare();
            }
            case CommonDeclare -> {
                idInstance = commonDeclareService.save((CommonDeclare) declare).getIdOtherDeclare();
                saveCurrentUsage(declareDataAsset);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
        assetDeclareService.save(createAssetDeclare(declareDataAsset, idInstance));
    }

    public void save(IDeclare ideclare, Integer idAsset, AssetDeclareDto declare) throws ValidateFiledException {
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class,
                declare.getBluePrintDeclare().getTypeDeclare());
        Integer idInstance;
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                idInstance = houseDeclareService.save((HouseDeclare) ideclare).getIdHouseDeclare();
            }
            case GroundDeclare -> {
                idInstance = groundDeclareService.save((GroundDeclare) ideclare).getIdGroundDeclare();
            }
            case CommonDeclare -> {
                idInstance = commonDeclareService.save((CommonDeclare) ideclare).getIdOtherDeclare();
                copyCurrentUsage(declare, idAsset);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
        assetDeclareService.save(copyAssetDeclare(declare, idInstance, idAsset));
    }

    private void saveCurrentUsage(Map<String, Object> declareDataAsset) {
        Integer idAsset = ValueUtil.getIntegerByObject(declareDataAsset.get("idAsset"));
        List<HashMap<String,Object>> idsCurrentUsage = (List<HashMap<String,Object>>) declareDataAsset.get("currentUsage");
        if (!CollectionUtils.isEmpty(idsCurrentUsage)) {
            List<AssetCurrentUsage> assetCurrentUsageList = new ArrayList<>();
            String timeCurrent = String.valueOf(new Date().getTime());
            for (HashMap<String,Object> obj : idsCurrentUsage) {
                AssetCurrentUsage assetCurrentUsage = new AssetCurrentUsage();
                assetCurrentUsage.setIdAsset(idAsset);
                assetCurrentUsage.setIdCurrentUsage(ValueUtil.getIntegerByObject(obj.get("idCurrentUsage")));
                assetCurrentUsage.setTimeCreated(timeCurrent);
                assetCurrentUsageList.add(assetCurrentUsage);
            }
            assetCurrentUsageService.saveAll(assetCurrentUsageList);
        }
    }

    private void copyCurrentUsage(AssetDeclareDto declare, Integer idAsset) {
        CommonDeclareDetailsDto detailsDto = (CommonDeclareDetailsDto) declare.getDataDetail();
        if (!CollectionUtils.isEmpty(detailsDto.getAssetCurrentUsageDetailsDto())) {
            List<AssetCurrentUsage> assetCurrentUsageList  = new ArrayList<>();
            String timeCurrent = String.valueOf(new Date().getTime());
            for (AssetCurrentUsageDetailsDto currentUsageDetailsDtoRoot : detailsDto.getAssetCurrentUsageDetailsDto()) {
                AssetCurrentUsage assetCurrentUsage = new AssetCurrentUsage();
                assetCurrentUsage.setIdAsset(idAsset);
                assetCurrentUsage.setIdCurrentUsage(currentUsageDetailsDtoRoot.getIdCurrentUsage());
                assetCurrentUsage.setTimeCreated(timeCurrent);
                assetCurrentUsageList.add(assetCurrentUsage);
            }
            assetCurrentUsageService.saveAll(assetCurrentUsageList);
        }
    }

    private AssetDeclare createAssetDeclare(Map<String, Object> declareDataAsset, Integer idInstance) {
        AssetDeclare declare = new AssetDeclare();
        declare.setIdAsset(ValueUtil.getIntegerByObject(ValueUtil.getIntegerByObject(declareDataAsset.get("idAsset"))));
        declare.setIdDeclare(ValueUtil.getIntegerByObject(ValueUtil.getIntegerByObject(declareDataAsset.get("idDeclare"))));
        declare.setIdInstance(idInstance);
        String timeCurrent = String.valueOf(new Date().getTime());
        declare.setTimeCreated(timeCurrent);
        declare.setTimeModified(timeCurrent);
        return assetDeclareService.save(declare);
    }

    private AssetDeclare copyAssetDeclare(AssetDeclareDto declareDto, Integer idInstance, Integer idAsset) {
        AssetDeclare declare = new AssetDeclare();
        declare.setIdAsset(idAsset);
        declare.setIdDeclare(declareDto.getBluePrintDeclare().getIdDeclare());
        declare.setIdInstance(idInstance);
        String timeCurrent = String.valueOf(new Date().getTime());
        declare.setTimeCreated(timeCurrent);
        declare.setTimeModified(timeCurrent);
        return assetDeclareService.save(declare);
    }

    public void validateDataDeclare(Map<String,Object> dataDeclare) {
        String typeDeclare = ValueUtil.getStringByObject(dataDeclare.get(Constants.KEY_TYPE_DECLARE));
        Declare declare = declareService.findDeclareByHardCodeAndVisible(typeDeclare, Constants.DECLARE_VISIBLE);
        dataDeclare.put("idDeclare", declare.getIdDeclare());
    }

    public <T> Object findDataDetailByTypeDeclareAndIdInstance(String typeDeclare, Integer idInstance) throws ValidateFiledException, IllegalAccessException {
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                return houseDeclareService.findHouseDeclareDetailsDtoById(idInstance);
            }
            case GroundDeclare -> {
                return groundDeclareService.findGroundDeclareDetailsDtoById(idInstance);
            }
            case CommonDeclare -> {
                CommonDeclareDetailsDto res = commonDeclareService.findCommonDeclareDetailsDtoById(idInstance);
                res.setAssetCurrentUsageDetailsDto(assetCurrentUsageService.findAssetCurrentUsageDetailsByIdAsset(res.getIdAsset()));
                return res;
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
    }

    public BluePrintDeclareDto findBluePrintAssetDeclareByIdAsset(Integer idAsset) {
        return assetDeclareService.findBluePrintAssetDeclareByIdAsset(idAsset);
    }

    public void deleteAssetDeclare(BluePrintDeclareDto bluePrintDeclareDto, Integer idAsset) throws ValidateFiledException {
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, bluePrintDeclareDto.getTypeDeclare());
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                houseDeclareService.deleteHouseDeclareById(bluePrintDeclareDto.getIdInstance());
            }
            case GroundDeclare -> {
                groundDeclareService.deleteGroundDeclareById(bluePrintDeclareDto.getIdInstance());
            }
            case CommonDeclare -> {
                commonDeclareService.deleteCommonDeclareById(bluePrintDeclareDto.getIdInstance());
                assetCurrentUsageService.deleteAssetCurrentUsageServiceByIdAsset(idAsset);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
        assetDeclareService.deleteAssetDeclareByIdInstanceAndIdDeclare(bluePrintDeclareDto.getIdInstance(),
                bluePrintDeclareDto.getIdDeclare());
    }

    public IDeclare findIDeclareByTypeDeclareAndIdInstance(String typeDeclare, Integer idInstance) throws ValidateFiledException {
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                return houseDeclareService.findHouseDeclareById(idInstance);
            }
            case GroundDeclare -> {
                return groundDeclareService.findGroundDeclareById(idInstance);
            }
            case CommonDeclare -> {
                return commonDeclareService.findCommonDeclareById(idInstance);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
    }

    public <T> IDeclare update(Map<String,Object> declareAsset, T dataDeclare) throws ValidateFiledException {
        String typeDeclare = ValueUtil.getStringByObject(declareAsset.get(Constants.KEY_TYPE_DECLARE));
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                return houseDeclareService.save((HouseDeclare) dataDeclare);
            }
            case GroundDeclare -> {
                return groundDeclareService.save((GroundDeclare) dataDeclare);
            }
            case CommonDeclare -> {
                editAssetCurrentUsage(declareAsset);
                return commonDeclareService.save((CommonDeclare) dataDeclare);
            }
            default -> {
                throw new ValidateFiledException("Don't exits type declare!");
            }
        }
    }

    private void editAssetCurrentUsage(Map<String, Object> declareAsset) {
        List<Map<String, Object>> assetCurrentUsageData = (List<Map<String, Object>>) declareAsset.get("currentUsage");
        List<AssetCurrentUsage> assetCurrentUsages =
                assetCurrentUsageService.findByIdAsset(ValueUtil.getIntegerByObject(declareAsset.get("idAsset")));
        deleteAssetCurrentUsage(assetCurrentUsages, assetCurrentUsageData);
        createNewAssetCurrentUsage(assetCurrentUsages, assetCurrentUsageData, ValueUtil.getIntegerByObject(declareAsset.get("idAsset")));
    }

    private void createNewAssetCurrentUsage(List<AssetCurrentUsage> assetCurrentUsages,
                                            List<Map<String, Object>> assetCurrentUsageData,
                                            Integer idAsset) {
        for (Map<String, Object> cud  : assetCurrentUsageData){
            boolean isCheckExits = false;
            for (AssetCurrentUsage usage : assetCurrentUsages){
                if (ValueUtil.getIntegerByObject(cud.get("idCurrentUsage")).equals(usage.getIdCurrentUsage())){
                    isCheckExits = true;
                    break;

                }
            }
            if (!isCheckExits) {
                createNewAssetCurrent(cud,idAsset);
            }
        }
    }

    private void createNewAssetCurrent(Map<String, Object> cud, Integer idAsset) {
        AssetCurrentUsage assetCurrentUsage = new AssetCurrentUsage();
        assetCurrentUsage.setIdAsset(idAsset);
        assetCurrentUsage.setIdCurrentUsage(ValueUtil.getIntegerByObject(cud.get("idCurrentUsage")));
        assetCurrentUsage.setTimeCreated(String.valueOf(new Date().getTime()));
        assetCurrentUsageService.save(assetCurrentUsage);
    }

    private void deleteAssetCurrentUsage(List<AssetCurrentUsage> assetCurrentUsages,
                                         List<Map<String, Object>> assetCurrentUsageData) {
        for (AssetCurrentUsage usage : assetCurrentUsages){
            boolean isCheckExits = false;
            for (Map<String, Object> cud : assetCurrentUsageData){
                if (usage.getIdCurrentUsage().equals(ValueUtil.getIntegerByObject(cud.get("idCurrentUsage")))){
                    isCheckExits = true;
                    break;
                }
            }

            if (!isCheckExits) {
                assetCurrentUsageService.deleteAssetCurrentUsage(usage);
            }
        }
    }
}
