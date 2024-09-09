package com.example.csvccdshustbe.factory.declare.impl;

import com.example.csvccdshustbe.entity.GroundDeclare;
import com.example.csvccdshustbe.entity.IDeclare;
import com.example.csvccdshustbe.factory.declare.DeclareFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Date;
import java.util.Map;

public class GroundDeclareFactory implements DeclareFactory {

    @Override
    public IDeclare createDeclare(Map<String, Object> mapDeclareRequest) {
        GroundDeclare declare = new GroundDeclare();
        declare.setIdAsset(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idAsset")));
        declare.setIdGoalsUseGround(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idGoalsUseGround")));
        declare.setWorkplace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("workplace")));
        declare.setHdsnNoBussiness(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnNoBussiness")));
        declare.setHdsnBussiness(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnBussiness")));
        declare.setHdsnRent(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnRent")));
        declare.setHdsnBonds(ValueUtil.getDoubleByObject(mapDeclareRequest.get("hdsnBonds")));
        declare.setLivePlace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("livePlace")));
        declare.setBlankPlace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("blankPlace")));
        declare.setEncroachedPlace(ValueUtil.getDoubleByObject(mapDeclareRequest.get("encroachedPlace")));
        declare.setSyntheticUse(ValueUtil.getDoubleByObject(mapDeclareRequest.get("syntheticUse")));
        declare.setOtherUse(ValueUtil.getDoubleByObject(mapDeclareRequest.get("otherUse")));
        declare.setAcreage(ValueUtil.getDoubleByObject(mapDeclareRequest.get("acreage")));
        declare.setLicenseCertificateUseGround(ValueUtil.getStringByObject(mapDeclareRequest.get("licenseCertificateUseGround")));
        declare.setDateLicenseCertificateUseGround(ValueUtil.getStringByObject(mapDeclareRequest.get("dateLicenseCertificateUseGround")));
        declare.setNumberDecisionDeliverGround(ValueUtil.getStringByObject(mapDeclareRequest.get("numberDecisionDeliverGround")));
        declare.setDateNumberDecisionDeliverGround(ValueUtil.getStringByObject(mapDeclareRequest.get("dateNumberDecisionDeliverGround")));
        declare.setContractNumberRentGround(ValueUtil.getStringByObject(mapDeclareRequest.get("contractNumberRentGround")));
        declare.setDateContractNumberRentGround(ValueUtil.getStringByObject(mapDeclareRequest.get("dateContractNumberRentGround")));
        declare.setAnotherContract(ValueUtil.getStringByObject(mapDeclareRequest.get("anotherContract")));
        declare.setIdTypeDeclareAsset(ValueUtil.getIntegerByObject(mapDeclareRequest.get("idTypeDeclareAsset")));
        String timeCurrent = String.valueOf(new Date().getTime());
        declare.setTimeCreated(timeCurrent);
        declare.setTimeModified(timeCurrent);
        declare.setContractNumberTransferGround(ValueUtil.getStringByObject(mapDeclareRequest.get("contractNumberTransferGround")));
        declare.setDateContractNumberTransferGround(ValueUtil.getStringByObject(mapDeclareRequest.get("dateContractNumberTransferGround")));
        declare.setNumberDecisionRentGround(ValueUtil.getStringByObject(mapDeclareRequest.get("numberDecisionRentGround")));
        declare.setDateNumberDecisionRentGround(ValueUtil.getStringByObject(mapDeclareRequest.get("dateNumberDecisionRentGround")));
        return declare;
    }
}
