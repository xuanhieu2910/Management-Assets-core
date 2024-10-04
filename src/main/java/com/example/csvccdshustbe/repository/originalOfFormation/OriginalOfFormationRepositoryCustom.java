package com.example.csvccdshustbe.repository.originalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OriginalOfFormationRepositoryCustom {

    Page<FindAllOriginalOfFormationDto> findAllOriginalOfFormationVisible(Pageable pageable, FindAllOriginalOfFormationVisibleRequest request);
    Page<FindAllOriginalOfFormationDto> findAllOriginalOfFormation(Pageable pageable, FindAllOriginalOfFormationRequest request);

    Optional<OriginalOfFormation> findOriginalOfFormationByName(String name);

    Optional<OriginalOfFormation> findOriginalOfFormationByIdParent(Integer idParent);

    Optional<OriginalOfFormation> findOriginalOfFormationById(Integer idOriginalOfFormation);

    boolean checkExitsOriginalOfFormationByNameOrShortNameOrCodeName(String name,String codeName,String shortName);

    boolean isCheckAssetByIdOriginalOfFormation(Integer idOriginalOfFormation);

    List<OriginalOfFormation> findAllOriginalOfFormationById(List<Integer> OriginalOfFormationId);
}
