package com.example.csvccdshustbe.service.transition.impl;

import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.entity.Transition;
import com.example.csvccdshustbe.repository.transition.TransitionRepository;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.transition.TransitionService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class TransitionServiceImpl implements TransitionService {

    @Autowired
    TransitionRepository transitionRepository;
    @Lazy
    @Autowired
    ProcessService processService;

    @Override
    public Transition saveTransition(Transition transition) {
        return transitionRepository.save(transition);
    }

    @Override
    public Transition findTransitionByIdProcess(Integer idProcess) {
        Optional<Transition> transition = transitionRepository.findTransitionByIdProcess(idProcess);
        if (transition.isEmpty()){
            throw new NotFoundException("Don't exits transition by id process!");
        }
        return transition.get();
    }


}
