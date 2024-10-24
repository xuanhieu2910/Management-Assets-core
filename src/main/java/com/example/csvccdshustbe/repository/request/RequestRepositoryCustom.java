package com.example.csvccdshustbe.repository.request;

import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import com.example.csvccdshustbe.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepositoryCustom {
    Optional<Request> findRequestByIdRequest(Integer idRequest);
    List<Request> findAllRequestByIdState(Integer idState);
    List<RequestDetailsDto> findRequestDetailsByIdState(Integer idState);
}
