package com.example.csvccdshustbe.service.typeState;

import com.example.csvccdshustbe.entity.TypeState;
import com.example.csvccdshustbe.response.typeState.FindAllTypeStateResponse;

import java.util.List;

public interface TypeStateService {
    List<TypeState> findAllTypeStateByCodes(List<String> codes);

    List<FindAllTypeStateResponse> findAllTypeStateActive();
}
