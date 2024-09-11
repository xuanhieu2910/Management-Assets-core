package com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.impl;

import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;
import com.example.csvccdshustbe.repository.otherVehicleTransportModule.OtherVehicleTransportRepository;
import com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.OtherVehicleTransportModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class OtherVehicleTransportModuleServiceImpl implements OtherVehicleTransportModuleService {


    @Autowired
    OtherVehicleTransportRepository otherVehicleTransportRepository;

    @Override
    public OtherVehicleTransportModule save(OtherVehicleTransportModule module) {
        return otherVehicleTransportRepository.save(module);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public Map<String, Object> findOtherVehicleTransportModuleByIdOtherVehicleTransport(Integer id) {
        Optional<OtherVehicleTransportModule> module = otherVehicleTransportRepository.findOtherVehicleTransportById(id);
        if (!module.isPresent()) {
            throw new NotFoundException("Don't exits other vehicle transport modules!");
        }
        return null;
    }
}
