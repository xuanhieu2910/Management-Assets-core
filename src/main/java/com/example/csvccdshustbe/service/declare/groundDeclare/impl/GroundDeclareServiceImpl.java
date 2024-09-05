package com.example.csvccdshustbe.service.declare.groundDeclare.impl;

import com.example.csvccdshustbe.entity.GroundDeclare;
import com.example.csvccdshustbe.repository.groundDeclare.GroundDeclareRepository;
import com.example.csvccdshustbe.service.declare.groundDeclare.GroundDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroundDeclareServiceImpl implements GroundDeclareService {

    @Autowired
    GroundDeclareRepository groundDeclareRepository;
    @Override
    public GroundDeclare save(GroundDeclare groundDeclare) {
        return groundDeclareRepository.save(groundDeclare);
    }
}
