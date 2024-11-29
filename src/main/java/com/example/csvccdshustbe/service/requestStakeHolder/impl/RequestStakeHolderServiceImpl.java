package com.example.csvccdshustbe.service.requestStakeHolder.impl;

import com.example.csvccdshustbe.dto.requestStakeHolder.RequestStakeHolderDetails;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Reason;
import com.example.csvccdshustbe.entity.RequestStakeHolder;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.requestStakeHolder.RequestStakeHolderRepository;
import com.example.csvccdshustbe.request.requestStakeHolder.ApprovedDocumentProcessInventoryRequest;
import com.example.csvccdshustbe.request.requestStakeHolder.ApprovedRequestStakeHolderRequest;
import com.example.csvccdshustbe.service.reason.ReasonService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import com.example.csvccdshustbe.utility.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RequestStakeHolderServiceImpl implements RequestStakeHolderService {

    @Autowired
    RequestStakeHolderRepository requestStakeHolderRepository;
    @Autowired
    ReasonService reasonService;
    @Lazy
    @Autowired
    RequestService requestService;

    @Override
    public List<RequestStakeHolder> createNewRequestStakeHolder(List<RequestStakeHolder> stakeHolder) {
        return requestStakeHolderRepository.saveAll(stakeHolder);
    }

    @Transactional
    @Override
    public void approvedRequestStakeHolder(ApprovedRequestStakeHolderRequest request) throws ValidateFiledException,
            JsonProcessingException, IllegalAccessException {
        validateDataApprovedRequestStakeHolder(request);
        RequestStakeHolder stakeHolder = findRequestStakeHolderByIdRequestStakeHolder(request.getIdRequestStakeHolder());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (request.getStatus().equals(Constants.STATUS_REQUEST_STAKE_HOLDER_FALSE)){
            Reason reason = reasonService.findReasonByIdReason(request.getIdReason());
            stakeHolder.setIdUser(csvcUser.getIdUser());
            stakeHolder.setIdDepartment(csvcUser.getIdDepartmentCurrent());
            stakeHolder.setStatus(request.getStatus());
            stakeHolder.setIdReason(reason.getIdReason());
            stakeHolder.setDescription(request.getDescription());
            stakeHolder.setTimeModified(String.valueOf(new Date().getTime()));
        } else {
            stakeHolder.setStatus(request.getStatus());
            stakeHolder.setTimeModified(String.valueOf(new Date().getTime()));
            stakeHolder.setIdUser(csvcUser.getIdUser());
            stakeHolder.setIdDepartment(csvcUser.getIdDepartmentCurrent());
        }
        requestStakeHolderRepository.save(stakeHolder);
        requestService.updateStatusRequestByIdRequest(stakeHolder.getIdRequest());
    }

//    @Override
//    public void approvedInventory(ApprovedDocumentProcessInventoryRequest request) {
//
//    }

    private void validateDataApprovedRequestStakeHolder(ApprovedRequestStakeHolderRequest request) throws ValidateFiledException {
        if (request.getStatus().equals(Constants.STATUS_REQUEST_STAKE_HOLDER_FALSE)){
            if (StringUtils.isBlank(request.getDescription()) || Objects.isNull(request.getIdReason())){
                throw new ValidateFiledException("Validate data request!");
            }
        }
    }

    @Override
    public RequestStakeHolder findRequestStakeHolderByIdRequestStakeHolder(Integer idRequestStakeHolder) {
        Optional<RequestStakeHolder> stakeHolder = requestStakeHolderRepository.findRequestStakeHolderById(idRequestStakeHolder);
        if (stakeHolder.isEmpty()){
            throw new NotFoundException("Don't exist request stake holder by id!");
        }
        return stakeHolder.get();
    }

    @Override
    public List<RequestStakeHolder> findRequestStakeHolderByIdRequest(Integer idRequest) {
        return requestStakeHolderRepository.findRequestStakeHolderByIdRequest(idRequest);
    }

    @Override
    public List<RequestStakeHolderDetails> findRequestStakeHolderDetailsByIdRequest(Integer idRequest) {
        return requestStakeHolderRepository.findRequestStakeHolderDetailsByIdRequest(idRequest);
    }
}
