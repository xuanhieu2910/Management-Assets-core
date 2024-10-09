package com.example.csvccdshustbe.repository.typeUse;


import com.example.csvccdshustbe.dto.typeUse.FindAllTypeUseDto;
import com.example.csvccdshustbe.entity.TypeUse;
import com.example.csvccdshustbe.request.typeUse.FindAllTypeUseRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypeUseRepositoryCustom {
    Page<TypeUse> findAllTypeUseActiveResponse(FindAllTypeUseRequest request, Pageable pageable);

    Optional<TypeUse> findTypeUseByName(String name);


    Optional<TypeUse> findTypeUseById(Integer idTypeUse);

    List<FindAllTypeUseDto> findAllTypeUserDtoToDownload();
}
