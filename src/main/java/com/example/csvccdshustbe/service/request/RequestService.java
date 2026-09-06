package com.example.csvccdshustbe.service.request;

import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RequestService {
    Request createNewRequestProcess(Request request);
    void updateStatusRequestByIdRequest(Integer idRequest) throws ValidateFiledException, JsonProcessingException, IllegalAccessException;
    List<Request> findAllRequestByIdState(Integer idState);
    List<RequestDetailsDto> findRequestDetailsByIdState(Integer idState);
}
