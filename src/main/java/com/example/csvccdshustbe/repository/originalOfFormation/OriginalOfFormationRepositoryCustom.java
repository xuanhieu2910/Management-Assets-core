package com.example.csvccdshustbe.repository.originalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.FindAllOriginalOfFormationDto;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OriginalOfFormationRepositoryCustom {

    Page<FindAllOriginalOfFormationDto> findAllOriginalOfFormationVisible(Pageable pageable, FindAllOriginalOfFormationRequest request);


}
