package com.example.csvccdshustbe.service.modules.otherAssetModule.impl;

import com.example.csvccdshustbe.dto.modules.otherAssetModules.OtherAssetModulesDetailsDto;
import com.example.csvccdshustbe.entity.OtherAssetModule;
import com.example.csvccdshustbe.repository.otherAssetModule.OtherAssetModuleRepository;
import com.example.csvccdshustbe.service.modules.otherAssetModule.OtherAssetModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

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
    public Map<String, Object> findOtherAssetModuleByIdOtherAssetModule(Integer idOtherAssetModule) throws IllegalAccessException {
        Optional<OtherAssetModulesDetailsDto> assetModule = otherAssetModuleRepository.
                findOtherAssetModuleDetailsDtoByIdOtherAssetModule(idOtherAssetModule);
        if (!assetModule.isPresent()){
            throw new NotFoundException("Don't exits other asset modules!");
        }
        return ValueUtil.convertObjectToMap(assetModule.get());
    }
}
