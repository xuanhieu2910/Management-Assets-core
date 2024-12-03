package com.example.csvccdshustbe.repository.typeProcess;

import com.example.csvccdshustbe.entity.TypeProcess;
import com.example.csvccdshustbe.request.typeProcess.FindAllTypeProcessRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TypeProcessRepositoryCustom {

    Optional<TypeProcess> findTypeProcessByCodeAndStatus(String codeProcess, Integer status);

    Optional<TypeProcess> findTypeProcessByIdAndStatus(Integer idTypeProcess, Integer status);
    Page<TypeProcess> findAllTypeProcessActive(FindAllTypeProcessRequest typeProcessRequest, Pageable pageable);
}
