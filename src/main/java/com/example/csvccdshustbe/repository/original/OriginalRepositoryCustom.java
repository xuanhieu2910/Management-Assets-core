package com.example.csvccdshustbe.repository.original;

import com.example.csvccdshustbe.dto.original.FindAllOriginalDto;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OriginalRepositoryCustom {

    Page<FindAllOriginalDto> findAllOriginalDtoByIdAssetCategory(FindAllOriginalVisibleRequest request, Pageable pageable);
}
