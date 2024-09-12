package com.example.csvccdshustbe.service.declare.commonDeclare;

import com.example.csvccdshustbe.entity.CommonDeclare;

import java.util.Map;

public interface CommonDeclareService {

    CommonDeclare save(CommonDeclare declare);

    Map<String, Object> findCommonDeclareDetailsDtoById(Integer idInstance) throws IllegalAccessException;
}
