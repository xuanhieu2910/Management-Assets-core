package com.example.csvccdshustbe.service.request.impl;

import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.repository.request.RequestRepository;
import com.example.csvccdshustbe.service.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {


    @Autowired
    RequestRepository requestRepository;

    @Override
    public Request createNewRequestProcess(Request request) {
        return requestRepository.save(request);
    }
}
