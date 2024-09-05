package com.example.csvccdshustbe.factory.asset.impl;

import com.example.csvccdshustbe.dto.asset.CreateAssetDto;
import com.example.csvccdshustbe.entity.Asset;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.factory.asset.AssetsFactory;
import com.example.csvccdshustbe.request.asset.CreateAssetRequest;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.Map;

public class ConcreteAssetFactory implements AssetsFactory {
    @Override
    public Asset createAsset(Map<String,Object> createAsset) {
        Asset asset = new Asset();
        asset.setName(ValueUtil.getStringByObject(createAsset.get("name")));
        asset.setCodeAsset(ValueUtil.getStringByObject(createAsset.get("codeAsset")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(createAsset.get("idAssetCategory")));
        asset.setIdDepartment(ValueUtil.getIntegerByObject(createAsset.get("idDepartment")));
        asset.setIdLocation(ValueUtil.getIntegerByObject(createAsset.get("idLocation")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(createAsset.get("idUnit")));
        asset.setIdOriginal(ValueUtil.getIntegerByObject(createAsset.get("idOriginal")));
        asset.setIdOriginOfFormation(ValueUtil.getIntegerByObject(createAsset.get("idOriginOfFormation")));
        asset.setIdProjects(ValueUtil.getIntegerByObject(createAsset.get("idProjects")));
        asset.setPurpose(ValueUtil.getStringByObject(createAsset.get("purpose")));
        asset.setNotes(ValueUtil.getStringByObject(createAsset.get("notes")));
        asset.setFileAttack(ValueUtil.getStringByObject(createAsset.get("fileAttack")));
        asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(createAsset.get("idDepartmentDefault")));
        String timeCurrent = String.valueOf(new Date().getTime());
        asset.setTimeCreated(timeCurrent);
        asset.setTimeModified(timeCurrent);
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asset.setIdUserCreated(csvcUser.getIdUser());
        asset.setIdUserModified(csvcUser.getIdUser());
        return new Asset();
    }
}
