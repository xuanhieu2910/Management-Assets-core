package com.example.csvccdshustbe.service.reason.impl;

import com.example.csvccdshustbe.dto.reason.FindAllReasonDto;
import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.repository.reason.ReasonRepository;
import com.example.csvccdshustbe.request.reason.FindAllReasonsRequest;
import com.example.csvccdshustbe.request.reason.FindAllTypeActionReasonsRequest;
import com.example.csvccdshustbe.response.reason.FindAllReasonResponse;
import com.example.csvccdshustbe.service.reason.ReasonService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import com.example.csvccdshustbe.utility.PageUtils;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @Override
    public Page<FindAllReasonResponse> findReasonsByTypeAction(FindAllTypeActionReasonsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllReasonDto> dtos = reasonRepository.findReasonsByTypeActionAndStatus(request, pageable);
        return new PageImpl<>(convertToFindTypeActionnReason(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }


    @Override
    public Page<FindAllReasonResponse> findAllReasonsResponse(FindAllReasonsRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllReasonDto> dtos = reasonRepository.findAllReasonResponse(request, pageable);
        return new PageImpl<>(convertToFindReason(dtos.get().collect(Collectors.toList())),
                pageable, dtos.getTotalElements());
    }

    private List<FindAllReasonResponse> convertToFindReason(List<FindAllReasonDto> collect) {
        List<FindAllReasonResponse> responses = new ArrayList<>();
        for (FindAllReasonDto allReasonDto: collect){
            FindAllReasonResponse res = new FindAllReasonResponse();
            res.setIdReason(allReasonDto.getIdReason());
            res.setName(allReasonDto.getName());
            res.setTypeReason(allReasonDto.getTypeReason());
            res.setTimeCreated(DateUtil.formatToPattern(new Date(allReasonDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            res.setTimeModified(DateUtil.formatToPattern(new Date(allReasonDto.getTimeModified()), DateUtil.DATE_FORMAT));
            res.setStatus(allReasonDto.getStatus());
            responses.add(res);
        }
        return responses;
    }

    private List<FindAllReasonResponse> convertToFindTypeActionnReason(List<FindAllReasonDto> collect) {
        List<FindAllReasonResponse> responses = new ArrayList<>();
        for (FindAllReasonDto allReasonDto: collect){
            FindAllReasonResponse res = new FindAllReasonResponse();
            res.setIdReason(allReasonDto.getIdReason());
            res.setName(allReasonDto.getName());
            res.setTypeReason(allReasonDto.getTypeReason());
            res.setTimeCreated(DateUtil.formatToPattern(new Date(allReasonDto.getTimeCreated()), DateUtil.DATE_FORMAT));
            res.setTimeModified(DateUtil.formatToPattern(new Date(allReasonDto.getTimeModified()), DateUtil.DATE_FORMAT));
            res.setStatus(allReasonDto.getStatus());
            res.setTypeAction(allReasonDto.getTypeAction());
            responses.add(res);
        }
        return responses;
    }

}
