package com.example.csvccdshustbe.repository.carModule;

import com.example.csvccdshustbe.dto.modules.carModules.CarModulesDetailsDto;
import com.example.csvccdshustbe.entity.CarModule;

import java.util.Optional;

public interface CarModuleRepositoryCustom {


    Optional<CarModulesDetailsDto> findCarModulesDetailsDtoByIdCar(Integer idCarModule);

    void deleteCarModuleByIdCarModule(Integer idInstance);
}
