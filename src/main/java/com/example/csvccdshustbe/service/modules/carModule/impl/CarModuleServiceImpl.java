package com.example.csvccdshustbe.service.modules.carModule.impl;

import com.example.csvccdshustbe.entity.CarModule;
import com.example.csvccdshustbe.repository.carModule.CarModuleRepository;
import com.example.csvccdshustbe.service.modules.carModule.CarModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CarModuleServiceImpl implements CarModuleService {

    @Autowired
    CarModuleRepository carModuleRepository;

    @Override
    public CarModule save(CarModule carModule) {
        return carModuleRepository.save(carModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findCarModuleByIdCarModule(Integer idCarModule) {
        return null;
    }
}
