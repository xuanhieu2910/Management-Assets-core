package com.example.csvccdshustbe.service.state;

import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.entity.State;
import com.example.csvccdshustbe.response.state.StateDetailsResponse;

import java.util.List;

public interface StateService {

    List<State> saveAllState(List<State> stateList);
    void updateStatusStateByIdState(Integer idState);
    StateDetailsResponse findStateDetailByIdState(Integer idState);
}
