package com.example.csvccdshustbe.service.originalOfFormation;

import com.example.csvccdshustbe.dto.originalOfFormation.AssetOriginalOfFormDto;
import com.example.csvccdshustbe.entity.OriginalOfFormation;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.originalOfFormation.CreateOriginalOfFormationReuqest;
import com.example.csvccdshustbe.request.originalOfFormation.FindAllOriginalOfFormationRequest;
import com.example.csvccdshustbe.request.originalOfFormation.UpdateOriginalOfFormationRequest;
import com.example.csvccdshustbe.response.originalOfFormation.FindAllOriginalOfFormationResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OriginalOfFormationService {

    Page<FindAllOriginalOfFormationResponse> findAllOriginalOfFormation(FindAllOriginalOfFormationRequest request);

    void createOriginalOfFormationService(CreateOriginalOfFormationReuqest request) throws ValidateFiledException;

    void updateOriginalOfFormationService(UpdateOriginalOfFormationRequest request) throws ValidateFiledException;

    void deleteOriginalOfFormationServiceById(Integer idOriginalOfFormation);
}
