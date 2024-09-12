package com.example.csvccdshustbe.repository.commonDeclare;

import com.example.csvccdshustbe.dto.declare.CommonDeclareDetailsDto;

import java.util.Optional;

public interface CommonDeclareRepositoryCustom {

    Optional<CommonDeclareDetailsDto> findCommonDeclareDetailDtoById(Integer idCommonDeclare);
}
