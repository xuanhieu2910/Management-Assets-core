package com.example.csvccdshustbe.service.originalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OriginalOfFormationService {

    Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request);
}
