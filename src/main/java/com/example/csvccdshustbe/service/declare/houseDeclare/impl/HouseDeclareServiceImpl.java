package com.example.csvccdshustbe.service.declare.houseDeclare.impl;

import com.example.csvccdshustbe.dto.declare.HouseDeclareDetailsDto;
import com.example.csvccdshustbe.entity.HouseDeclare;
import com.example.csvccdshustbe.repository.houseDeclare.HouseDeclareRepository;
import com.example.csvccdshustbe.service.declare.houseDeclare.HouseDeclareService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class HouseDeclareServiceImpl implements HouseDeclareService {

    @Autowired
    HouseDeclareRepository houseDeclareRepository;

    @Override
    public HouseDeclare save(HouseDeclare houseDeclare) {
        return houseDeclareRepository.save(houseDeclare);
    }

    @Override
    public HouseDeclareDetailsDto findHouseDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException {
        Optional<HouseDeclareDetailsDto> detailsDto = houseDeclareRepository.findHouseDeclareDetailsById(idInstance);
        if (detailsDto.isEmpty()) {
            throw new NotFoundException("Don't exits house declare!");
        }
        return detailsDto.get();
    }

    @Override
    public void deleteHouseDeclareById(Integer idInstance) {
        houseDeclareRepository.deleteHouseDeclareById(idInstance);
    }

    @Override
    public HouseDeclare findHouseDeclareById(Integer idInstance) {
        Optional<HouseDeclare> houseDeclare = houseDeclareRepository.findHouseDeclareById(idInstance);
        if (houseDeclare.isEmpty()){
            throw new NotFoundException("Don't exits house declare");
        }
        return houseDeclare.get();
    }
}
