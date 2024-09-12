package com.example.csvccdshustbe.repository.houseDeclare;

import com.example.csvccdshustbe.dto.declare.HouseDeclareDetailsDto;

import java.util.Optional;

public interface HouseDeclareRepositoryCustom {

    Optional<HouseDeclareDetailsDto> findHouseDeclareDetailsById(Integer id);
}
