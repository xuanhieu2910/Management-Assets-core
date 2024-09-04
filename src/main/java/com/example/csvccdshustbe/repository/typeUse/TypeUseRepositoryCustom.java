package com.example.csvccdshustbe.repository.typeUse;


import com.example.csvccdshustbe.entity.TypeUse;

import java.util.List;
import java.util.Optional;

public interface TypeUseRepositoryCustom {
    List<TypeUse> findAllTypeUseResponseByStatus(Integer status);

    Optional<TypeUse> findTypeUseByName(String name);


    Optional<TypeUse> findTypeUseById(Integer idTypeUse);
}
