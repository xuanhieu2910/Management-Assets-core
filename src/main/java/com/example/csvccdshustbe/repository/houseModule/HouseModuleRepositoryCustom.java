package com.example.csvccdshustbe.repository.houseModule;

import com.example.csvccdshustbe.dto.modules.houseModules.HouseModuleDetailsDto;
import com.example.csvccdshustbe.entity.HouseModule;

import java.util.Optional;

public interface HouseModuleRepositoryCustom {

    Optional<HouseModuleDetailsDto> findHouseModuleDetailsDtoByIdHouseModule(Integer idHouseModule);

    void deleteHouseModuleByIdHouseModule(Integer idInstance);
}
