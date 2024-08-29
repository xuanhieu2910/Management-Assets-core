package com.example.csvccdshustbe.service.originalOfFormation;

import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import org.springframework.data.domain.Page;

public interface OriginalOfFormationService {

    Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request);
}
