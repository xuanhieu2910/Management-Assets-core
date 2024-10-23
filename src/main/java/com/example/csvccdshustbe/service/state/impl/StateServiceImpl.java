package com.example.csvccdshustbe.service.state.impl;

import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.entity.State;
import com.example.csvccdshustbe.entity.Transition;
import com.example.csvccdshustbe.repository.state.StateRepository;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.state.StateService;
import com.example.csvccdshustbe.service.transition.TransitionService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateRepository stateRepository;
    @Autowired
    TransitionService transitionService;
    @Lazy
    @Autowired
    RequestService requestService;


    @Override
    public List<State> saveAllState(List<State> stateList) {
        return stateRepository.saveAll(stateList);
    }

    @Override
    public void updateStatusStateByIdState(Integer idState, List<Request> requests) {
        Optional<State> state = stateRepository.findStateByIdState(idState);
        if (state.isEmpty()){
            throw new NotFoundException("Don't exits state by id state!");
        }
        updateStatusStateCurrent(state.get(), requests);
    }

    private void handleStateNext(State state) {
        Transition transition = transitionService.findTransitionByIdProcess(state.getIdProcess());
        Optional<State> stateOptional = stateRepository.findStateByIdProcessAndStepNext(state.getIdProcess(), state.getStep() + 1);
        if(stateOptional.isPresent()) {
            transition.setIdStateCurrent(transition.getIdStateNext());
            transition.setIdStateNext(stateOptional.get().getIdState());
        } else {
            transition.setIdStateCurrent(transition.getIdStateNext());
        }
        transitionService.saveTransition(transition);
        if (transition.getIdStateCurrent().equals(transition.getIdStateNext())){
            transitionService.updateStatusProcessByIdProcess(transition.getIdProcess(), Constants.STATUS_SUCCESS_PROCESS);
        }
        handleRequest(state);
    }

    private void handleRequest(State state) {
    }

    private void updateStatusStateCurrent(State state, List<Request> requests) {
        for (Request request: requests){
            if (request.getStatus().equals(Constants.STATUS_REQUEST_PENDING)) {
                return;
            } else if (request.getStatus().equals(Constants.STATUS_REQUEST_FALSE)){
                state.setStatus(Constants.STATUS_STATE_FALSE);
                state.setTimeModified(String.valueOf(new Date().getTime()));
                return;
            }
        }
        state.setStatus(Constants.STATUS_STATE_SUCCESS);
        state.setTimeModified(String.valueOf(new Date().getTime()));
        stateRepository.save(state);
        handleStateNext(state);
    }
}
