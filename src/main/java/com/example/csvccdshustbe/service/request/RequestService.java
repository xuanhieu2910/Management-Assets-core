package com.example.csvccdshustbe.service.request;

import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import com.example.csvccdshustbe.entity.Request;

import java.util.List;

public interface RequestService {
    Request createNewRequestProcess(Request request);
    void updateStatusRequestByIdRequest(Integer idRequest);
    List<Request> findAllRequestByIdState(Integer idState);
    List<RequestDetailsDto> findRequestDetailsByIdState(Integer idState);
}
