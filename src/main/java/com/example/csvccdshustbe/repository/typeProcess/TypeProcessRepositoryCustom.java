package com.example.csvccdshustbe.repository.typeProcess;

import com.example.csvccdshustbe.entity.TypeProcess;

import java.util.Optional;

public interface TypeProcessRepositoryCustom {

    Optional<TypeProcess> findTypeProcessByCode(String codeProcess);
}
