package com.example.csvccdshustbe.repository.original;

import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.entity.Original;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OriginalRepositoryCustom {

    Page<FindAllOriginalDto> findAllOriginalDtoByIdAssetCategory(FindAllOriginalVisibleRequest request, Pageable pageable);

    Optional<Original> findOriginalByHardCodeAndStatus(String hardCode, Integer status);
    Optional<Original> findOriginalByName(String name);
}
