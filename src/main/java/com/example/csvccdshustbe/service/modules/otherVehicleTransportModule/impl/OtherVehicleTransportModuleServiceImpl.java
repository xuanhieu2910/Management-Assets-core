package com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.impl;

import com.example.csvccdshustbe.dto.modules.otherVehicleTransportModules.OtherVehicleTransportModuleDetailsDto;
import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;
import com.example.csvccdshustbe.repository.otherVehicleTransportModule.OtherVehicleTransportRepository;
import com.example.csvccdshustbe.service.modules.otherVehicleTransportModule.OtherVehicleTransportModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
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
    public OtherVehicleTransportModuleDetailsDto findOtherVehicleTransportDetailsModuleByIdOtherVehicleTransport(Integer id)  {
        Optional<OtherVehicleTransportModuleDetailsDto> module = otherVehicleTransportRepository.
                findOtherVehicleTransportDetailsDtoById(id);
        if (module.isEmpty()) {
            throw new NotFoundException("Don't exits other vehicle transport modules!");
        }
        return module.get();
    }

    @Override
    public void deleteOtherVehicleTransportById(Integer idInstance) {
        otherVehicleTransportRepository.deleteOtherVehicleTransportById(idInstance);
    }

    @Override
    public OtherVehicleTransportModule findOtherVehicleTransportModuleByIdOtherVehicleTransport(Integer idInstance) {
        Optional<OtherVehicleTransportModule> transportModule =
                otherVehicleTransportRepository.findOtherVehicleTransportModuleByIdOtherVehicle(idInstance);
        if (transportModule.isEmpty()){
            throw new NotFoundException("Don't exits vehicle transport module");
        }
        return transportModule.get();
    }
}
