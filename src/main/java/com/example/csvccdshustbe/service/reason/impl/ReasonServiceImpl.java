package com.example.csvccdshustbe.service.reason.impl;

import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.repository.reason.ReasonRepository;
import com.example.csvccdshustbe.service.reason.ReasonService;
import com.example.csvccdshustbe.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@Service
public class ReasonServiceImpl implements ReasonService {

    @Autowired
    ReasonRepository reasonRepository;

    @Override
    public Reason findReasonByIdReason(Integer idReason) {
        Optional<Reason> reason = reasonRepository.findReasonByIdReasonAndStatus(idReason, Constants.STATUS_REASON_ACTIVE);
        if (reason.isEmpty()){
            throw new NotFoundException("Don't exits reason!");
        }
        return reason.get();
    }
}
