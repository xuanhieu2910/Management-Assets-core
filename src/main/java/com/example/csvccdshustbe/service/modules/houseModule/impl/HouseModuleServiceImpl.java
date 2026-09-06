package com.example.csvccdshustbe.service.modules.houseModule.impl;

import com.example.csvccdshustbe.dto.modules.houseModules.HouseModuleDetailsDto;
import com.example.csvccdshustbe.entity.HouseModule;
import com.example.csvccdshustbe.repository.houseModule.HouseModuleRepository;
import com.example.csvccdshustbe.service.modules.houseModule.HouseModuleService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class HouseModuleServiceImpl implements HouseModuleService {

    @Autowired
    HouseModuleRepository houseModuleRepository;

    @Override
    public HouseModule save(HouseModule houseModule) {
        return houseModuleRepository.save(houseModule);
    }

    @Override
    public void validateDataCreate(Map<String, Object> dataModule) {

    }

    @Override
    public HouseModuleDetailsDto findHouseModuleDetailsByIdHouseModule(Integer houseModule) throws IllegalAccessException {
        Optional<HouseModuleDetailsDto> module = houseModuleRepository.findHouseModuleDetailsDtoByIdHouseModule(houseModule);
        if (module.isEmpty()){
            throw new NotFoundException("Don't exits house modules!");
        }
        return module.get();
    }

    @Override
    public void deleteHouseModuleById(Integer idInstance) {
        houseModuleRepository.deleteHouseModuleByIdHouseModule(idInstance);
    }

    @Override
    public HouseModule findHouseModuleByIdHouseModule(Integer idInstance) {
        Optional<HouseModule> houseModule = houseModuleRepository.findHouseModuleByIdHouseModule(idInstance);
        if (houseModule.isEmpty()){
            throw new NotFoundException("Don't exits house module by id!");
        }
        return houseModule.get();
    }
}
