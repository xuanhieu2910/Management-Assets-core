package com.example.csvccdshustbe.repository.reason;

import com.example.csvccdshustbe.dto.reason.FindAllReasonDto;
import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.request.reason.FindAllReasonsRequest;
import com.example.csvccdshustbe.request.reason.FindAllTypeActionReasonsRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReasonRepositoryCustom {
    Optional<Reason> findReasonByIdReasonAndStatus(Integer idReason, Integer status);

    Page<FindAllReasonDto> findReasonsByTypeActionAndStatus(FindAllTypeActionReasonsRequest request, Pageable pageable);

    Page<FindAllReasonDto> findAllReasonResponse( FindAllReasonsRequest request,Pageable pageable);
}
