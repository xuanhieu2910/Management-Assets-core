package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.entity.HouseModule;
import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class HouseModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        HouseModule houseModule = new HouseModule();
        houseModule.setIdAsset(ValueUtil.getIntegerByObject(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset"))));
        houseModule.setIsManageGround(ValueUtil.getIntegerByObject(mapModuleCreate.get("isManageGround")));
        if (houseModule.getIsManageGround()!= null && houseModule.getIsManageGround().equals(Constants.HOUSE_MODULES_IS_MANAGE_HOUSE)) {
            houseModule.setIdInstance(ValueUtil.getIntegerByObject(mapModuleCreate.get("idInstance")));
        }
        houseModule.setProvinceCode(ValueUtil.getStringByObject(mapModuleCreate.get("provinceCode")));
        houseModule.setDistrictCode(ValueUtil.getStringByObject(mapModuleCreate.get("districtCode")));
        houseModule.setWardCode(ValueUtil.getStringByObject(mapModuleCreate.get("wardCode")));
        houseModule.setAddressDetail(ValueUtil.getStringByObject(mapModuleCreate.get("addressDetail")));
        houseModule.setFloorsNumber(ValueUtil.getIntegerByObject(mapModuleCreate.get("floorsNumber")));
        houseModule.setAcreage(ValueUtil.getDoubleByObject(mapModuleCreate.get("acreage")));
        houseModule.setPublishYear(ValueUtil.getStringByObject(mapModuleCreate.get("publishYear")));
        return houseModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        HouseModule houseModule = (HouseModule) iModules;
        houseModule.setIsManageGround(ValueUtil.getIntegerByObject(mapModuleUpdate.get("isManageGround")));
        if (houseModule.getIsManageGround().equals(Constants.HOUSE_MODULES_IS_MANAGE_HOUSE)) {
            houseModule.setIdInstance(ValueUtil.getIntegerByObject(mapModuleUpdate.get("idInstance")));
        }
        houseModule.setProvinceCode(ValueUtil.getStringByObject(mapModuleUpdate.get("provinceCode")));
        houseModule.setDistrictCode(ValueUtil.getStringByObject(mapModuleUpdate.get("districtCode")));
        houseModule.setWardCode(ValueUtil.getStringByObject(mapModuleUpdate.get("wardCode")));
        houseModule.setAddressDetail(ValueUtil.getStringByObject(mapModuleUpdate.get("addressDetail")));
        houseModule.setFloorsNumber(ValueUtil.getIntegerByObject(mapModuleUpdate.get("floorsNumber")));
        houseModule.setAcreage(ValueUtil.getDoubleByObject(mapModuleUpdate.get("acreage")));
        houseModule.setPublishYear(ValueUtil.getStringByObject(mapModuleUpdate.get("publishYear")));
        return houseModule;
    }

    @Override
    public IModules copyModule(AssetModulesDto assetModulesDto, Integer idAsset) {
        HouseModule houseModuleRoot = (HouseModule) assetModulesDto.getDataDetails();
        HouseModule houseModule = new HouseModule();
        houseModule.setIdAsset(idAsset);
        houseModule.setIsManageGround(houseModuleRoot.getIsManageGround());
        if (houseModule.getIsManageGround().equals(Constants.HOUSE_MODULES_IS_MANAGE_HOUSE)) {
            houseModule.setIdInstance(houseModule.getIdInstance());
        }
        houseModule.setProvinceCode(houseModuleRoot.getProvinceCode());
        houseModule.setDistrictCode(houseModuleRoot.getDistrictCode());
        houseModule.setWardCode(houseModuleRoot.getWardCode());
        houseModule.setAddressDetail(houseModuleRoot.getAddressDetail());
        houseModule.setFloorsNumber(houseModuleRoot.getFloorsNumber());
        houseModule.setAcreage(houseModuleRoot.getAcreage());
        houseModule.setPublishYear(houseModuleRoot.getPublishYear());
        return houseModule;
    }
}
