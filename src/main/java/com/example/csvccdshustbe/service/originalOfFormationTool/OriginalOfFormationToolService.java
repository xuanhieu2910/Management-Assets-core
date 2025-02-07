package com.example.csvccdshustbe.service.originalOfFormationTool;

import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;

import java.util.List;

public interface OriginalOfFormationToolService {
    List<FindAllOriginalOfFormationToolDto> findAllOriginalOfFormationDtoByVisible(Integer visible);
}
