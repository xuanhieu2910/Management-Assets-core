package com.example.csvccdshustbe.service.transition;


import com.example.csvccdshustbe.entity.Transition;

import java.util.Optional;

public interface TransitionService {

    Transition saveTransition(Transition transition);
    Transition findTransitionByIdProcess(Integer idProcess);
    void updateStatusProcessByIdProcess(Integer idProcess, Integer status);
}
