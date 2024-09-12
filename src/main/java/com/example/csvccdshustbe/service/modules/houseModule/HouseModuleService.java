package com.example.csvccdshustbe.service.modules.houseModule;

import com.example.csvccdshustbe.entity.HouseModule;

import java.util.Map;

public interface HouseModuleService {

    HouseModule save(HouseModule houseModule);

    void validateDataCreate(Map<String, Object> dataModule);

    Map<String, Object> findHouseModuleDetailsByIdHouseModule(Integer houseModule) throws IllegalAccessException;

    void deleteHouseModuleById(Integer idInstance);

    HouseModule findHouseModuleByIdHouseModule(Integer idInstance);
}
