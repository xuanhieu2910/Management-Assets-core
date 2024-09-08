package com.example.csvccdshustbe.service.modules.houseModule;

import com.example.csvccdshustbe.entity.HouseModule;

import java.util.Map;

public interface HouseModuleService {

    HouseModule save(HouseModule houseModule);

    void validateDataCreate(Map<String, Object> dataModule);
}
