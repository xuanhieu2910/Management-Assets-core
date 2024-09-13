package com.example.csvccdshustbe.service.declare.houseDeclare;

import com.example.csvccdshustbe.entity.HouseDeclare;
import com.example.csvccdshustbe.entity.IDeclare;

import java.util.Map;

public interface HouseDeclareService {

    HouseDeclare save(HouseDeclare declare);

    Map<String, Object> findHouseDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException;

    void deleteHouseDeclareById(Integer idInstance);

    HouseDeclare findHouseDeclareById(Integer idInstance);
}
