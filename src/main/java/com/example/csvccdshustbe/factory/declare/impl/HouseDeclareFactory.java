package com.example.csvccdshustbe.factory.declare.impl;

import com.example.csvccdshustbe.entity.HouseDeclare;
import com.example.csvccdshustbe.entity.IDeclare;
import com.example.csvccdshustbe.factory.declare.DeclareFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class HouseDeclareFactory implements DeclareFactory {


    @Override
    public IDeclare createDeclare(Map<String, Object> mapDeclareRequest) {
        HouseDeclare declare = new HouseDeclare();
        declare.setIdAsset(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idAsset")));
        declare.setWorkplace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("workplace")));
        declare.setHdsnNoBussiness(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnNoBussiness")));
        declare.setHdsnBussiness(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnBussiness")));
        declare.setHdsnRent(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnRent")));
        declare.setHdsnBonds(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnBonds")));
        declare.setLivePlace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("LivePlace")));
        declare.setBlankPlace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("blankPlace")));
        declare.setEncroachedPlace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("encroachedPlace")));
        declare.setSyntheticUse(ValueUtil.getDoubleByObject(mapDeclareRequest.get("syntheticUse")));
        declare.setOtherUse(ValueUtil.getDoubleByObject(mapDeclareRequest.get("otherUse")));
        declare.setAcreage(ValueUtil.getDoubleByObject(mapDeclareRequest.get("acreage")));
        declare.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idTypeDeclareAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        declare.setTimeCreated(timeCurrent);
        declare.setTimeModified(timeCurrent);
        return declare;
    }

    @Override
    public IDeclare updateDeclare(Map<String, Object> declareDataAsset, IDeclare iDeclareDetails) {
        HouseDeclare declare = (HouseDeclare) iDeclareDetails;
        declare.setWorkplace(ValueUtil.getDoubleByObject(declareDataAsset.get("workplace")));
        declare.setHdsnNoBussiness(ValueUtil.getDoubleByObject(declareDataAsset.get("hdsnNoBussiness")));
        declare.setHdsnBussiness(ValueUtil.getDoubleByObject(declareDataAsset.get("hdsnBussiness")));
        declare.setHdsnRent(ValueUtil.getDoubleByObject(declareDataAsset.get("hdsnRent")));
        declare.setHdsnBonds(ValueUtil.getDoubleByObject(declareDataAsset.get("hdsnBonds")));
        declare.setLivePlace(ValueUtil.getDoubleByObject(declareDataAsset.get("LivePlace")));
        declare.setBlankPlace(ValueUtil.getDoubleByObject(declareDataAsset.get("blankPlace")));
        declare.setEncroachedPlace(ValueUtil.getDoubleByObject(declareDataAsset.get("encroachedPlace")));
        declare.setSyntheticUse(ValueUtil.getDoubleByObject(declareDataAsset.get("syntheticUse")));
        declare.setOtherUse(ValueUtil.getDoubleByObject(declareDataAsset.get("otherUse")));
        declare.setAcreage(ValueUtil.getDoubleByObject(declareDataAsset.get("acreage")));
        declare.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(declareDataAsset.get("idTypeDeclareAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        declare.setTimeModified(timeCurrent);
        return declare;
    }
}
