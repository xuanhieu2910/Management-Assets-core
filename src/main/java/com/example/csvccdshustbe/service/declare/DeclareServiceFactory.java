package com.example.csvccdshustbe.service.declare;


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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    public void save(IDeclare declare, Map<String,Object> declareDataAsset) throws ValidateFiledException {
        String typeDeclare = ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE));
        EnumDeclareFactory enumDeclareFactory = Enum.valueOf(EnumDeclareFactory.class, typeDeclare);
        Integer idInstance;
        switch (enumDeclareFactory){
            case HouseDeclare -> {
                idInstance = houseDeclareService.save((HouseDeclare) declare).getIdArchitectureDeclare();
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

    private void saveCurrentUsage(Map<String, Object> declareDataAsset) {
        Integer idAsset = ValueUtil.getIntegerByObject(declareDataAsset.get("idAsset"));
        List<Object[]> idsCurrentUsage = (List<Object[]>) declareDataAsset.get("currentUsage");
        if (!CollectionUtils.isEmpty(idsCurrentUsage)) {
            List<AssetCurrentUsage> assetCurrentUsageList = new ArrayList<>();
            String timeCurrent = String.valueOf(new Date().getTime());
            for (Object[] obj : idsCurrentUsage) {
                AssetCurrentUsage assetCurrentUsage = new AssetCurrentUsage();
                assetCurrentUsage.setIdAsset(idAsset);
                assetCurrentUsage.setIdCurrentUsage(ValueUtil.getIntegerByObject(obj[0]));
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
}
