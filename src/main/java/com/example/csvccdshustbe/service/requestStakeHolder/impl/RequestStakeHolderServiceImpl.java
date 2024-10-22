package com.example.csvccdshustbe.service.requestStakeHolder.impl;

import com.example.csvccdshustbe.entity.RequestStakeHolder;
import com.example.csvccdshustbe.repository.requestStakeHolder.RequestStakeHolderRepository;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestStakeHolderServiceImpl implements RequestStakeHolderService {

    @Autowired
    RequestStakeHolderRepository requestStakeHolderRepository;

    @Override
    public List<RequestStakeHolder> createNewRequestStakeHolder(List<RequestStakeHolder> stakeHolder) {
        return requestStakeHolderRepository.saveAll(stakeHolder);
    }
}
