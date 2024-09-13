package com.example.csvccdshustbe.service.declare.groundDeclare;

import com.example.csvccdshustbe.entity.GroundDeclare;
import com.example.csvccdshustbe.entity.IDeclare;

import java.util.Map;

public interface GroundDeclareService {

    GroundDeclare save(GroundDeclare groundDeclare);

    Map<String, Object> findGroundDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException;

    void deleteGroundDeclareById(Integer idInstance);

    GroundDeclare findGroundDeclareById(Integer idInstance);
}
