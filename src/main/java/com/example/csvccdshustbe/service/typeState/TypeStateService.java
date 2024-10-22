package com.example.csvccdshustbe.service.typeState;

import com.example.csvccdshustbe.entity.TypeState;

import java.util.List;

public interface TypeStateService {
    List<TypeState> findAllTypeStateByCodes(List<String> codes);

}
