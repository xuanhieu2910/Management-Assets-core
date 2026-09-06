package com.example.csvccdshustbe.service.typeState.impl;

import com.example.csvccdshustbe.entity.TypeState;
import com.example.csvccdshustbe.repository.typeState.TypeStateRepository;
import com.example.csvccdshustbe.response.typeState.FindAllTypeStateResponse;
import com.example.csvccdshustbe.service.typeState.TypeStateService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeStateServiceImpl implements TypeStateService {

    @Autowired
    TypeStateRepository typeStateRepository;

    @Override
    public List<TypeState> findAllTypeStateByCodes(List<String> codes) {
        return typeStateRepository.findTypeStatesByListCodeAndStatus(codes, Constants.STATUS_TYPE_STATE_ACTIVE);
    }

    @Override
    public List<FindAllTypeStateResponse> findAllTypeStateActive() {
        List<TypeState> typeStates = typeStateRepository.findAllTypeStateByStatus(Constants.STATUS_TYPE_STATE_ACTIVE);
        return convertToFindAllTypeState(typeStates);
    }

    private List<FindAllTypeStateResponse> convertToFindAllTypeState(List<TypeState> typeStates) {
        List<FindAllTypeStateResponse> responses = new ArrayList<>();
        for (TypeState typeState : typeStates){
            FindAllTypeStateResponse response = new FindAllTypeStateResponse();
            response.setIdTypeState(typeState.getIdTypeState());
            response.setCodeTypeState(typeState.getCode());
            response.setNameTypeState(typeState.getName());
            responses.add(response);
        }
        return responses;
    }
}
