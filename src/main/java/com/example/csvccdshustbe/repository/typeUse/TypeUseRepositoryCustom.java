package com.example.csvccdshustbe.repository.typeUse;

import com.example.csvccdshustbe.entity.TypeUse;

import java.util.List;

public interface TypeUseRepositoryCustom {
    List<TypeUse> findAllTypeUseResponseByStatus(Integer status);
}
