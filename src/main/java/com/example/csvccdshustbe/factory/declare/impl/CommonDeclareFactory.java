package com.example.csvccdshustbe.factory.declare.impl;

import com.example.csvccdshustbe.entity.CommonDeclare;
import com.example.csvccdshustbe.entity.IDeclare;
import com.example.csvccdshustbe.factory.declare.DeclareFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;


public class CommonDeclareFactory implements DeclareFactory {


    @Override
    public IDeclare createDeclare(Map<String, Object> mapDeclareRequest) {
        CommonDeclare declare = new CommonDeclare();
        declare.setIdAsset(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idAsset")));
        declare.setSpecification(ValueUtil.getStringByObject(mapDeclareRequest.get("specification")));
        declare.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idTypeDeclareAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        declare.setTimeCreated(timeCurrent);
        declare.setTimeModified(timeCurrent);
        return declare;
    }
}
