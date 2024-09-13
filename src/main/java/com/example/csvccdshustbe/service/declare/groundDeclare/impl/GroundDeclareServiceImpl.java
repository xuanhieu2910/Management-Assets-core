package com.example.csvccdshustbe.service.declare.groundDeclare.impl;

import com.example.csvccdshustbe.dto.declare.GroundDeclareDetailsDto;
import com.example.csvccdshustbe.entity.GroundDeclare;
import com.example.csvccdshustbe.repository.groundDeclare.GroundDeclareRepository;
import com.example.csvccdshustbe.service.declare.groundDeclare.GroundDeclareService;
import com.example.csvccdshustbe.utility.ValueUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Map;
import java.util.Optional;

@Service
public class GroundDeclareServiceImpl implements GroundDeclareService {

    @Autowired
    GroundDeclareRepository groundDeclareRepository;
    @Override
    public GroundDeclare save(GroundDeclare groundDeclare) {
        return groundDeclareRepository.save(groundDeclare);
    }

    @Override
    public Map<String, Object> findGroundDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException {
        Optional<GroundDeclareDetailsDto> detailsDto = groundDeclareRepository.findGroundDeclareDetailsDtoById(idInstance);
        if (!detailsDto.isPresent()){
            throw new NotFoundException("Don't exits ground declare details!");
        }
        return ValueUtil.convertObjectToMap(detailsDto.get());
    }

    @Override
    public void deleteGroundDeclareById(Integer idInstance) {
        groundDeclareRepository.deleteGroundDeclareById(idInstance);
    }

    @Override
    public GroundDeclare findGroundDeclareById(Integer idInstance) {
        Optional<GroundDeclare> groundDeclare = groundDeclareRepository.findGroundDeclareById(idInstance);
        if (groundDeclare.isEmpty()){
            throw new NotFoundException("Don't exits ground declare!");
        }
        return groundDeclare.get();
    }
}
