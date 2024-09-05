package com.example.csvccdshustbe.service.modules.houseModule.impl;

import com.example.csvccdshustbe.entity.HouseModule;
import com.example.csvccdshustbe.repository.houseModule.HouseModuleRepository;
import com.example.csvccdshustbe.service.modules.houseModule.HouseModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseModuleServiceImpl implements HouseModuleService {

    @Autowired
    HouseModuleRepository houseModuleRepository;

    @Override
    public HouseModule save(HouseModule houseModule) {
        return houseModuleRepository.save(houseModule);
    }
}
