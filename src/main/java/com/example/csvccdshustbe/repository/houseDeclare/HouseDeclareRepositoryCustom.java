package com.example.csvccdshustbe.repository.houseDeclare;

import com.example.csvccdshustbe.dto.declare.HouseDeclareDetailsDto;
import com.example.csvccdshustbe.entity.HouseDeclare;

import java.util.Optional;

public interface HouseDeclareRepositoryCustom {

    Optional<HouseDeclareDetailsDto> findHouseDeclareDetailsById(Integer id);

    void deleteHouseDeclareById(Integer idInstance);

    Optional<HouseDeclare> findHouseDeclareById(Integer idInstance);
}
