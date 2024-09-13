package com.example.csvccdshustbe.repository.groundDeclare;

import com.example.csvccdshustbe.dto.declare.GroundDeclareDetailsDto;
import com.example.csvccdshustbe.entity.GroundDeclare;

import java.util.Optional;

public interface GroundDeclareRepositoryCustom {

    Optional<GroundDeclareDetailsDto> findGroundDeclareDetailsDtoById(Integer idGroundDeclare);

    void deleteGroundDeclareById(Integer idInstance);

    Optional<GroundDeclare> findGroundDeclareById(Integer idInstance);
}
