package com.example.csvccdshustbe.repository.carModule;

import com.example.csvccdshustbe.entity.CarModule;

import java.util.Optional;

public interface CarModuleRepositoryCustom {


    Optional<CarModule> findCarModulesByIdCar(Integer idCarModule);
}
