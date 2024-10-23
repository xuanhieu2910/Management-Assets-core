package com.example.csvccdshustbe.repository.reason;

import com.example.csvccdshustbe.entity.Reason;

import java.util.Optional;

public interface ReasonRepositoryCustom {
    Optional<Reason> findReasonByIdReasonAndStatus(Integer idReason, Integer status);
}
