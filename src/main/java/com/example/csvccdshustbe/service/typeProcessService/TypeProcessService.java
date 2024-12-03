package com.example.csvccdshustbe.service.typeProcessService;

import com.example.csvccdshustbe.entity.TypeProcess;
import com.example.csvccdshustbe.request.typeProcess.FindAllTypeProcessRequest;
import com.example.csvccdshustbe.response.typeProcess.FindAllTypeProcessResponse;
import org.springframework.data.domain.Page;

public interface TypeProcessService {

    TypeProcess findTypeProcessByCode(String code);
    TypeProcess findTypeProcessByIdTypeProcess(Integer idTypeProcess);

    Page<FindAllTypeProcessResponse> findAllTypeProcess(FindAllTypeProcessRequest request);
}
