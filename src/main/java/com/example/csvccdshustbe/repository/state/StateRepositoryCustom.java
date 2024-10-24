package com.example.csvccdshustbe.repository.state;

import com.example.csvccdshustbe.entity.State;

import java.util.List;
import java.util.Optional;

public interface StateRepositoryCustom {
    Optional<State> findStateByIdState(Integer idState);
    Optional<State> findStateByIdProcessAndStepNext(Integer idProcess, Integer stepStateNext);

}
