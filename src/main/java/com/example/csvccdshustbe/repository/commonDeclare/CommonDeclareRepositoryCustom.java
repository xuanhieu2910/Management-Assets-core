package com.example.csvccdshustbe.repository.commonDeclare;

import com.example.csvccdshustbe.dto.declare.CommonDeclareDetailsDto;
import com.example.csvccdshustbe.entity.CommonDeclare;

import java.util.Optional;

public interface CommonDeclareRepositoryCustom {

    Optional<CommonDeclareDetailsDto> findCommonDeclareDetailDtoById(Integer idCommonDeclare);

    void deleteCommonDeclareById(Integer idInstance);

    Optional<CommonDeclare> findCommonDeclareByIdCommonDeclare(Integer idInstance);
}
