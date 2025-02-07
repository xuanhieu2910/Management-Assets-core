package com.example.csvccdshustbe.repository.originalOfFormationTool;

import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;

import java.util.List;

public interface OriginalOfFormationToolRepositoryCustom {

    List<FindAllOriginalOfFormationToolDto>
    findAllOriginalOfFormationDtoByVisible(Integer visible);

}
