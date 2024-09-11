package com.example.csvccdshustbe.repository.groundModule;

import com.example.csvccdshustbe.entity.GroundModule;

import java.util.Optional;

public interface GroundModuleRepositoryCustom {

    Optional<GroundModule> findGroundModuleByIdGroundModule(Integer idGroundModule);
}
