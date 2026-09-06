package com.example.csvccdshustbe.repository.typeState;

import com.example.csvccdshustbe.entity.TypeState;

import java.util.List;

public interface TypeStateRepositoryCustom {

    List<TypeState> findTypeStatesByListCodeAndStatus(List<String> codes, Integer status);
    List<TypeState> findAllTypeStateByStatus(Integer status);
}
