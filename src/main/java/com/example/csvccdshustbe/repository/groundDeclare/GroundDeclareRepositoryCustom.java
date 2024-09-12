package com.example.csvccdshustbe.repository.groundDeclare;

import com.example.csvccdshustbe.dto.declare.GroundDeclareDetailsDto;

import java.util.Optional;

public interface GroundDeclareRepositoryCustom {

    Optional<GroundDeclareDetailsDto> findGroundDeclareDetailsDtoById(Integer idGroundDeclare);
}
