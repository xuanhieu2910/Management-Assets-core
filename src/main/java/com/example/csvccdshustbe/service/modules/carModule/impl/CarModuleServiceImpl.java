package com.example.csvccdshustbe.service.modules.carModule.impl;

import com.example.csvccdshustbe.dto.modules.carModules.CarModulesDetailsDto;
import com.example.csvccdshustbe.entity.CarModule;
import com.example.csvccdshustbe.repository.carModule.CarModuleRepository;
import com.example.csvccdshustbe.service.modules.carModule.CarModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

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
    public Map<String, Object> findCarModuleByIdCarModule(Integer idCarModule) throws IllegalAccessException {
        Optional<CarModulesDetailsDto> carModule = carModuleRepository.findCarModulesDetailsDtoByIdCar(idCarModule);
        if (!carModule.isPresent()) {
            throw new NotFoundException("Don't exits car module!");
        }
        return ValueUtil.convertObjectToMap(carModule.get());
    }
}
