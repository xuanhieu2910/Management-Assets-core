package com.example.csvccdshustbe.repository.process;

import com.example.csvccdshustbe.dto.process.FindAllProcessAssetDto;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProcessRepositoryCustom {

    Page<FindAllProcessAssetDto> findAllProcessAssetDtoByIdsDepartment(FindAllProcessAssetRequest request, Pageable pageable);
}
