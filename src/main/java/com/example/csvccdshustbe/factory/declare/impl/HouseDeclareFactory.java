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
}
