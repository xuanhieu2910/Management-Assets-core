package com.example.csvccdshustbe.service.modules.carModule;

import com.example.csvccdshustbe.dto.modules.carModules.CarModulesDetailsDto;
import com.example.csvccdshustbe.entity.CarModule;

import java.util.Map;

public interface CarModuleService {

    CarModule save(CarModule carModule);

    void validateDataCreate(Map<String, Object> dataModule);

    CarModulesDetailsDto findCarModuleDetailsByIdCarModule(Integer idCarModule) throws IllegalAccessException;

    void deleteCarModuleById(Integer idInstance);

    CarModule findCarModuleByIdCarModule(Integer idInstance);
}
