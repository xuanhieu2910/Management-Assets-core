package com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.impl;

import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;
import com.example.csvccdshustbe.repository.otherVehicleTransportModule.OtherVehicleTransportRepository;
import com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.OtherVehicleTransportModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtherVehicleTransportModuleServiceImpl implements OtherVehicleTransportModuleService {


    @Autowired
    OtherVehicleTransportRepository otherVehicleTransportRepository;

    @Override
    public OtherVehicleTransportModule save(OtherVehicleTransportModule module) {
        return otherVehicleTransportRepository.save(module);
    }
}
