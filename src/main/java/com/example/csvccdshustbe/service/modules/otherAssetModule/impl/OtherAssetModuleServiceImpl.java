package com.example.csvccdshustbe.service.modules.otherAssetModule.impl;

import com.example.csvccdshustbe.entity.OtherAssetModule;
import com.example.csvccdshustbe.repository.otherAssetModule.OtherAssetModuleRepository;
import com.example.csvccdshustbe.service.modules.otherAssetModule.OtherAssetModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OtherAssetModuleServiceImpl implements OtherAssetModuleService {

    @Autowired
    OtherAssetModuleRepository otherAssetModuleRepository;

    @Override
    public OtherAssetModule save(OtherAssetModule otherAssetModule) {
        return otherAssetModuleRepository.save(otherAssetModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findOtherAssetModuleByIdOtherAssetModule(Integer idOtherAssetModule) {
        return null;
    }
}
