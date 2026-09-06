package com.example.csvccdshustbe.repository.transition;

import com.example.csvccdshustbe.entity.Transition;

import java.util.Optional;

public interface TransitionRepositoryCustom {
    Optional<Transition> findTransitionByIdProcess(Integer idProcess);
}
