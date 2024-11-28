package com.example.csvccdshustbe.service.request.impl;

import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import com.example.csvccdshustbe.entity.Request;
import com.example.csvccdshustbe.entity.RequestStakeHolder;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.request.RequestRepository;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import com.example.csvccdshustbe.service.state.StateService;
import com.example.csvccdshustbe.utility.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {


    @Autowired
    RequestRepository requestRepository;
    @Autowired
    RequestStakeHolderService requestStakeHolderService;
    @Autowired
    StateService stateService;



    @Override
    public Request createNewRequestProcess(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void updateStatusRequestByIdRequest(Integer idRequest) throws ValidateFiledException, JsonProcessingException, IllegalAccessException {
        Optional<Request> request = requestRepository.findRequestByIdRequest(idRequest);
        if (request.isEmpty()){
            throw new NotFoundException("Don't exist request by id request!");
        }
        List<RequestStakeHolder> requestStakeHolderList =
                requestStakeHolderService.findRequestStakeHolderByIdRequest(request.get().getIdRequest());
        updateStatusRequest(request.get(), requestStakeHolderList);
    }

    @Override
    public List<Request> findAllRequestByIdState(Integer idState) {
        return requestRepository.findAllRequestByIdState(idState);
    }

    @Override
    public List<RequestDetailsDto> findRequestDetailsByIdState(Integer idState) {
        return requestRepository.findRequestDetailsByIdState(idState);
    }


    private void updateStatusRequest(Request request, List<RequestStakeHolder> requestStakeHolderList)
            throws ValidateFiledException, JsonProcessingException, IllegalAccessException {
        for (RequestStakeHolder stakeHolder: requestStakeHolderList){
            if (stakeHolder.getStatus().equals(Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING)) {
                return;
            }
            else if (stakeHolder.getStatus().equals(Constants.STATUS_REQUEST_STAKE_HOLDER_FALSE)){
                request.setStatus(Constants.STATUS_REQUEST_FALSE);
                request.setTimeModified(String.valueOf(new Date().getTime()));
                requestRepository.save(request);
                stateService.updateStatusStateByIdState(request.getIdState());
                return;
            }
        }
        request.setStatus(Constants.STATUS_REQUEST_SUCCESS);
        request.setTimeModified(String.valueOf(new Date().getTime()));
        requestRepository.save(request);
        stateService.updateStatusStateByIdState(request.getIdState());
    }
}
