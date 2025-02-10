package com.example.csvccdshustbe.repository.originalOfFormationTool;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.dto.originalOfFormationTool.FindAllOriginalOfFormationToolDto;
import com.example.csvccdshustbe.entity.OriginalOfFormationTool;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationVisibleRequest;
import com.example.csvccdshustbe.request.originalOfFormationTool.FindAllOriginalOfFormationToolVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OriginalOfFormationToolRepositoryCustom {

    Page<FindAllOriginalOfFormationDto> findAllOriginalOfFormationToolVisible(Pageable pageable, FindAllOriginalOfFormationToolVisibleRequest request);
    Optional<OriginalOfFormationTool> findOriginalOfFormationToolByName(String name);
    Optional<OriginalOfFormationTool> findOriginalOfFormationToolByIdParent(Integer idParent);
    Optional<OriginalOfFormationTool> findOriginalOfFormationToolById(Integer idOriginalOfFormationTool);

    boolean checkExitsOriginalOfFormationToolByNameOrShortNameOrCodeName(String name,String codeName,String shortName);
    List<FindAllOriginalOfFormationToolDto> findAllOriginalOfFormationDtoByVisible(Integer visible);
}
