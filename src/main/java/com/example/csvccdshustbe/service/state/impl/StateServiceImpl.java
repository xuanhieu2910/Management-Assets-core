package com.example.csvccdshustbe.service.state.impl;

import com.example.csvccdshustbe.entity.State;
import com.example.csvccdshustbe.repository.state.StateRepository;
import com.example.csvccdshustbe.service.state.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateRepository stateRepository;

    @Override
    public List<State> saveAllState(List<State> stateList) {
        return stateRepository.saveAll(stateList);
    }
}
