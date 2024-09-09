package com.example.csvccdshustbe.factory.module.impl;

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
        if (ValueUtil.getIntegerByObject(mapModuleCreate.get("isManageHouse")).equals(Constants.HOUSE_MODULES_IS_MANAGE_HOUSE)) {
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
}
