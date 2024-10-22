package com.example.csvccdshustbe.service.requestData.impl;

import com.example.csvccdshustbe.entity.RequestData;
import com.example.csvccdshustbe.repository.requestData.RequestDataRepository;
import com.example.csvccdshustbe.service.requestData.RequestDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestDataServiceImpl implements RequestDataService {

    @Autowired
    RequestDataRepository requestDataRepository;

    @Override
    public RequestData createNewRequestData(RequestData data) {
        return requestDataRepository.save(data);
    }
}
