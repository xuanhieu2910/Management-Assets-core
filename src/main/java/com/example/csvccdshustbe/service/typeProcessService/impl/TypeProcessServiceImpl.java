package com.example.csvccdshustbe.service.typeProcessService.impl;

import com.example.csvccdshustbe.entity.TypeProcess;
import com.example.csvccdshustbe.repository.typeProcess.TypeProcessRepository;
import com.example.csvccdshustbe.request.typeProcess.FindAllTypeProcessRequest;
import com.example.csvccdshustbe.response.typeProcess.FindAllTypeProcessResponse;
import com.example.csvccdshustbe.service.typeProcessService.TypeProcessService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeProcessServiceImpl implements TypeProcessService {

    @Autowired
    TypeProcessRepository typeProcessRepository;

    @Override
    public TypeProcess findTypeProcessByCode(String code) {
        Optional<TypeProcess> process = typeProcessRepository.findTypeProcessByCodeAndStatus(code.trim(),
                Constants.STATUS_TYPE_PROCESS_ACTIVE);
        if (process.isEmpty()){
            throw new NotFoundException("Don't exist type process by code");
        }
        return process.get();
    }

    @Override
    public TypeProcess findTypeProcessByIdTypeProcess(Integer idTypeProcess) {
        Optional<TypeProcess> process = typeProcessRepository.findTypeProcessByIdAndStatus(idTypeProcess,
                Constants.STATUS_TYPE_PROCESS_ACTIVE);
        if (process.isEmpty()){
            throw new NotFoundException("Don't exist type process by code");
        }
        return process.get();
    }

    @Override
    public Page<FindAllTypeProcessResponse> findAllTypeProcess(FindAllTypeProcessRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<TypeProcess> typeProcesses = typeProcessRepository.findAllTypeProcessActive(request, pageable);
        return new PageImpl<>(convertToFindAllTypeProcessResponse(typeProcesses.getContent()), pageable, typeProcesses.getTotalElements());
    }

    private List<FindAllTypeProcessResponse> convertToFindAllTypeProcessResponse(List<TypeProcess> typeProcesses) {
        List<FindAllTypeProcessResponse> responses = new ArrayList<>();
        for (TypeProcess typeProcess : typeProcesses){
            FindAllTypeProcessResponse response = new FindAllTypeProcessResponse();
            response.setIdTypeProcess(typeProcess.getIdTypeProcess());
            response.setNameTypeProcess(typeProcess.getName());
            response.setCodeTypeProcess(typeProcess.getCode());
            responses.add(response);
        }
        return responses;
    }
}
