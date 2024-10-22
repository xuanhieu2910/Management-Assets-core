package com.example.csvccdshustbe.service.typeProcessService.impl;

import com.example.csvccdshustbe.entity.TypeProcess;
import com.example.csvccdshustbe.repository.typeProcess.TypeProcessRepository;
import com.example.csvccdshustbe.service.typeProcessService.TypeProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class TypeProcessServiceImpl implements TypeProcessService {

    @Autowired
    TypeProcessRepository typeProcessRepository;

    @Override
    public TypeProcess findTypeProcessByCode(String code) {
        Optional<TypeProcess> process = typeProcessRepository.findTypeProcessByCode(code.trim());
        if (process.isEmpty()){
            throw new NotFoundException("Don't exist type process by code");
        }
        return process.get();
    }
}
