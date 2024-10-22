package com.example.csvccdshustbe.service.typeState.impl;

import com.example.csvccdshustbe.entity.TypeState;
import com.example.csvccdshustbe.repository.typeState.TypeStateRepository;
import com.example.csvccdshustbe.service.typeState.TypeStateService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeStateServiceImpl implements TypeStateService {

    @Autowired
    TypeStateRepository typeStateRepository;

    @Override
    public List<TypeState> findAllTypeStateByCodes(List<String> codes) {
        return typeStateRepository.findTypeStatesByListCodeAndStatus(codes, Constants.STATUS_TYPE_STATE_ACTIVE);
    }
}
