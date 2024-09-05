package com.example.csvccdshustbe.service.declare.houseDeclare.impl;

import com.example.csvccdshustbe.entity.HouseDeclare;
import com.example.csvccdshustbe.repository.houseDeclare.HouseDeclareRepository;
import com.example.csvccdshustbe.service.declare.houseDeclare.HouseDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseDeclareServiceImpl implements HouseDeclareService {

    @Autowired
    HouseDeclareRepository houseDeclareRepository;

    @Override
    public HouseDeclare save(HouseDeclare houseDeclare) {
        return houseDeclareRepository.save(houseDeclare);
    }
}
