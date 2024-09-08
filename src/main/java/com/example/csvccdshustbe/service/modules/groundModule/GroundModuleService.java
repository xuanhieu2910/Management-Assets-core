package com.example.csvccdshustbe.service.modules.groundModule;

import com.example.csvccdshustbe.entity.GroundModule;

import java.util.Map;

public interface GroundModuleService {
    GroundModule saveGroundModule(GroundModule module);

    void validateDataCreate(Map<String, Object> dataModule);
}
