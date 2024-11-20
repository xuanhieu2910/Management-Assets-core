package com.example.csvccdshustbe.service.typeProcessService;

import com.example.csvccdshustbe.entity.TypeProcess;

public interface TypeProcessService {

    TypeProcess findTypeProcessByCode(String code);
    TypeProcess findTypeProcessByIdTypeProcess(Integer idTypeProcess);
}
