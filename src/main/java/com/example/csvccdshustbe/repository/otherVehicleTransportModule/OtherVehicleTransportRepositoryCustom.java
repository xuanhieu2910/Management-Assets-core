package com.example.csvccdshustbe.repository.otherVehicleTransportModule;

import com.example.csvccdshustbe.dto.modules.otherVehicleTransportModules.OtherVehicleTransportModuleDetailsDto;
import com.example.csvccdshustbe.entity.OtherVehicleTransportModule;

import java.util.Optional;

public interface OtherVehicleTransportRepositoryCustom {

    Optional<OtherVehicleTransportModuleDetailsDto> findOtherVehicleTransportDetailsDtoById(Integer idOtherVehicleTransport);
}
