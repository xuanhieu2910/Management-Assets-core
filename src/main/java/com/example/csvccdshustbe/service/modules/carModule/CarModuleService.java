package com.example.csvccdshustbe.service.modules.carModule;

import com.example.csvccdshustbe.entity.CarModule;

import java.util.Map;

public interface CarModuleService {

    CarModule save(CarModule carModule);

    void validateDataCreate(Map<String, Object> dataModule);

    Map<String,Object> findCarModuleByIdCarModule(Integer idCarModule) throws IllegalAccessException;
}
