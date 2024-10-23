package com.example.csvccdshustbe.repository.process;

import com.example.csvccdshustbe.entity.Process;

import java.util.Optional;

public interface ProcessRepositoryCustom {
    Optional<Process> findProcessByIdProcess(Integer idProcess);
}
