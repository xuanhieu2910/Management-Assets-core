package com.example.csvccdshustbe.service.originalOfFormation;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.originalOfFormation.CreateOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.UpdateOriginalOfFormationRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import org.springframework.data.domain.Page;

public interface OriginalOfFormationService {

    Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request);

    void createOriginalOfFormationService(CreateOriginalOfFormationRequest request) throws ValidateFiledException;

    void updateOriginalOfFormationService(UpdateOriginalOfFormationRequest request) throws ValidateFiledException;

    void deleteOriginalOfFormationServiceById(Integer idOriginalOfFormation);
}
