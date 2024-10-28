package com.example.csvccdshustbe.service.reason;

import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.request.reason.FindAllReasonsRequest;
import com.example.csvccdshustbe.response.reason.FindAllReasonResponse;
import org.springframework.data.domain.Page;

public interface ReasonService {

    Reason findReasonByIdReason(Integer idReason);
    Page<FindAllReasonResponse> findAllReasonsResponse(FindAllReasonsRequest request);
}
