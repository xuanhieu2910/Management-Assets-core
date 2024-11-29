package com.example.csvccdshustbe.service.state;

import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.entity.State;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.response.state.StateDetailsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface StateService {

    List<State> saveAllState(List<State> stateList);
    void updateStatusStateByIdState(Integer idState) throws ValidateFiledException, JsonProcessingException, IllegalAccessException;
    StateDetailsResponse findStateDetailByIdState(Integer idState);
}
