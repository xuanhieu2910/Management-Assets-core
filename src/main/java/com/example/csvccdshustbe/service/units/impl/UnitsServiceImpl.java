package com.example.csvccdshustbe.service.units.impl;

import com.example.csvccdshustbe.entity.Units;
import com.example.csvccdshustbe.repository.units.UnitsRepository;
import com.example.csvccdshustbe.service.units.UnitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitsServiceImpl implements UnitsService {

    @Autowired
    UnitsRepository unitsRepository;


    @Override
    public List<Units> findAllUnits() {
        return unitsRepository.findAllUnits();
    }
}
