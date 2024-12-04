package com.example.csvccdshustbe.service.reason;

import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.request.reason.FindAllReasonsRequest;
import com.example.csvccdshustbe.request.reason.FindAllTypeActionReasonsRequest;
import com.example.csvccdshustbe.response.reason.FindAllReasonResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReasonService {

    Reason findReasonByIdReason(Integer idReason);

    Page<FindAllReasonResponse> findReasonsByTypeAction(FindAllTypeActionReasonsRequest request);
    Page<FindAllReasonResponse> findAllReasonsResponse(FindAllReasonsRequest request);
}
