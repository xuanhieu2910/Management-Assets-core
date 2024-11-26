package com.example.csvccdshustbe.factory.module.impl;

import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.entity.GroundModule;
import com.example.csvccdshustbe.entity.IModules;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.utility.ValueUtil;

import java.util.Map;

public class GroundModuleFactory implements ModuleFactory {

    @Override
    public IModules createModule(Map<String, Object> mapModuleCreate) {
        GroundModule groundModule = new GroundModule();
        groundModule.setIdAsset(ValueUtil.getIntegerByObject(mapModuleCreate.get("idAsset")));
        groundModule.setProvinceCode(ValueUtil.getStringByObject(mapModuleCreate.get("provinceCode")));
        groundModule.setDistrictCode(ValueUtil.getStringByObject(mapModuleCreate.get("districtCode")));
        groundModule.setWardCode(ValueUtil.getStringByObject(mapModuleCreate.get("wardCode")));
        groundModule.setAddressDetail(ValueUtil.getStringByObject(mapModuleCreate.get("addressDetail")));
        return groundModule;
    }

    @Override
    public IModules updateModule(Map<String, Object> mapModuleUpdate, IModules iModules) {
        GroundModule groundModule = (GroundModule) iModules;
        groundModule.setProvinceCode(ValueUtil.getStringByObject(mapModuleUpdate.get("provinceCode")));
        groundModule.setDistrictCode(ValueUtil.getStringByObject(mapModuleUpdate.get("districtCode")));
        groundModule.setWardCode(ValueUtil.getStringByObject(mapModuleUpdate.get("wardCode")));
        groundModule.setAddressDetail(ValueUtil.getStringByObject(mapModuleUpdate.get("addressDetail")));
        return groundModule;
    }

    @Override
    public IModules createModule(AssetModulesDto assetModulesDto, Integer idAsset) {
        GroundModule groundModuleRoot = (GroundModule) assetModulesDto.getDataDetails();
        GroundModule groundModule = new GroundModule();
        groundModule.setIdAsset(groundModuleRoot.getIdAsset());
        groundModule.setProvinceCode(groundModuleRoot.getProvinceCode());
        groundModule.setDistrictCode(groundModuleRoot.getDistrictCode());
        groundModule.setWardCode(groundModuleRoot.getWardCode());
        groundModule.setAddressDetail(groundModuleRoot.getAddressDetail());
        return groundModule;
    }
}
