package com.example.csvccdshustbe.service.declare.commonDeclare.impl;

import com.example.csvccdshustbe.entity.CommonDeclare;
import com.example.csvccdshustbe.repository.commonDeclare.CommonDeclareRepository;
import com.example.csvccdshustbe.service.declare.commonDeclare.CommonDeclareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonDeclareServiceImpl implements CommonDeclareService {

    @Autowired
    CommonDeclareRepository commonDeclareRepository;


    @Override
    public CommonDeclare save(CommonDeclare declare) {
        return commonDeclareRepository.save(declare);
    }
}
