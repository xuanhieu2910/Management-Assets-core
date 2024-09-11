package com.example.csvccdshustbe.service.modules.houseModule.impl;

import com.example.csvccdshustbe.dto.modules.houseModules.HouseModuleDetailsDto;
import com.example.csvccdshustbe.entity.HouseModule;
import com.example.csvccdshustbe.repository.houseModule.HouseModuleRepository;
import com.example.csvccdshustbe.service.modules.houseModule.HouseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class HouseModuleServiceImpl implements HouseModuleService {

    @Autowired
    HouseModuleRepository houseModuleRepository;

    @Override
    public HouseModule save(HouseModule houseModule) {
        return houseModuleRepository.save(houseModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findHouseModuleByIdHouseModule(Integer houseModule) {
        Optional<HouseModuleDetailsDto> module = houseModuleRepository.findHouseModuleDetailsDtoByIdHouseModule(houseModule);
        if (!module.isPresent()){
            throw new NotFoundException("Don't exits house modules!");
        }
        return null;
    }
}
