package com.example.csvccdshustbe.service.declare.groundDeclare;

import com.example.csvccdshustbe.entity.GroundDeclare;

import java.util.Map;

public interface GroundDeclareService {

    GroundDeclare save(GroundDeclare groundDeclare);

    Map<String, Object> findGroundDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException;
}
