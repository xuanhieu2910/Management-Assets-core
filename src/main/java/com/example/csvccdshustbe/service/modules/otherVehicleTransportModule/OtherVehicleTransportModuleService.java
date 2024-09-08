package com.example.csvccdshustbe.service.modules.otherVehicleTransportModule;

import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;

import java.util.Map;

public interface OtherVehicleTransportModuleService {

    OtherVehicleTransportModule save(OtherVehicleTransportModule module);

    void validateDataCreate(Map<String, Object> dataModule);
}
