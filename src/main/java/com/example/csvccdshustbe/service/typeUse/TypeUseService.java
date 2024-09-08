package com.example.csvccdshustbe.service.typeUse;

import com.example.csvccdshustbe.exception.ValidateFiledException;

import com.example.csvccdshustbe.request.typeUse.CreateTypeUseRequest;
import com.example.csvccdshustbe.request.typeUse.FindAllTypeUseRequest;
import com.example.csvccdshustbe.request.typeUse.UpdateTypeUseRequest;
import com.example.csvccdshustbe.response.typeUse.FindAllTypeUseResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TypeUseService {
        Page<FindAllTypeUseResponse> findAllTypeUseActiveResponse(FindAllTypeUseRequest request);

        void createTypeUse(CreateTypeUseRequest request) throws ValidateFiledException;

        void updateTypeUse(UpdateTypeUseRequest request) throws ValidateFiledException;

        void deleteTypeUseByIdTypeUse(Integer idTypeUse);
}
