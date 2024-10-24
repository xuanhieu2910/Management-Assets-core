package com.example.csvccdshustbe.service.requestData;

import com.example.csvccdshustbe.dto.requestData.RequestDataDetailsDto;
import com.example.csvccdshustbe.entity.RequestData;

import java.util.List;

public interface RequestDataService {

    RequestData createNewRequestData(RequestData data);
    List<RequestDataDetailsDto> findRequestDataDetailsByIdRequest(Integer idRequest);
}
