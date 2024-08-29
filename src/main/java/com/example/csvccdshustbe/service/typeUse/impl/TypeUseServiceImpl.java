package com.example.csvccdshustbe.service.typeUse.impl;

import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.repository.typeUse.TypeUseRepository;
import com.example.csvccdshustbe.service.typeUse.TypeUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeUseServiceImpl implements TypeUseService{
    @Autowired
    TypeUseRepository typeUseRepository;
    @Override
    public List<TypeUse>findAllTypeUse(){
        return typeUseRepository.findAllTypeUse();
    }

}
