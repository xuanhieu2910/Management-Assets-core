package com.example.csvccdshustbe.repository.houseModule;

import com.example.csvccdshustbe.entity.HouseModule;

import java.util.Optional;

public interface HouseModuleRepositoryCustom {

    Optional<HouseModule> findHouseModuleByIdHouseModule(Integer idHouseModule);

}
