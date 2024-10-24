package com.example.csvccdshustbe.repository.requestData;

import com.example.csvccdshustbe.dto.requestData.RequestDataDetailsDto;

import java.util.List;

public interface RequestDataRepositoryCustom {
    List<RequestDataDetailsDto> findRequestDataDetailsByIdRequest(Integer idRequest);
}
