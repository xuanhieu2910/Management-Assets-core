package com.example.csvccdshustbe.service.originalOfFormation;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.originalOfFormation.*;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationVisibleResponse;
import org.springframework.data.domain.Page;

public interface OriginalOfFormationService {

    Page<FindAllOriginalOfFormationVisibleResponse> findAllOriginalOfFormationVisible(FindAllOriginalOfFormationVisibleRequest request);
    Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request);

    void createOriginalOfFormationService(CreateOriginalOfFormationRequest request) throws ValidateFiledException;

    void updateOriginalOfFormationService(UpdateOriginalOfFormationRequest request) throws ValidateFiledException;

    void deleteOriginalOfFormationServiceById(Integer idOriginalOfFormation) throws ValidateFiledException;

    void updateStatusOriginalOfFormation(UpdateStatusOriginalOfFormationRequest request) throws ValidateFiledException;
}
