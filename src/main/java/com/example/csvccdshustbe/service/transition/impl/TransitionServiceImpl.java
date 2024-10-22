package com.example.csvccdshustbe.service.transition.impl;

import com.example.csvccdshustbe.entity.Transition;
import com.example.csvccdshustbe.repository.transition.TransitionRepository;
import com.example.csvccdshustbe.service.transition.TransitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitionServiceImpl implements TransitionService {

    @Autowired
    TransitionRepository transitionRepository;

    @Override
    public Transition saveTransition(Transition transition) {
        return transitionRepository.save(transition);
    }
}
