package com.example.csvccdshustbe.service.typeUse;

import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.response.typeUse.FindAllTypeUseResponse;

import java.util.List;

public interface TypeUseService {
        List<FindAllTypeUseResponse> findAllTypeUseResponseByStatus(Integer Status);
}
