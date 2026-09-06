package com.example.csvccdshustbe.service.requestStakeHolder;

import com.example.csvccdshustbe.dto.requestStakeHolder.RequestStakeHolderDetails;
import com.example.csvccdshustbe.entity.RequestStakeHolder;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.requestStakeHolder.ApprovedDocumentProcessInventoryRequest;
import com.example.csvccdshustbe.request.requestStakeHolder.ApprovedRequestStakeHolderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface RequestStakeHolderService {

    List<RequestStakeHolder> createNewRequestStakeHolder(List<RequestStakeHolder> stakeHolder);
    void approvedRequestStakeHolder(ApprovedRequestStakeHolderRequest request) throws ValidateFiledException, JsonProcessingException, IllegalAccessException;

//    void approvedInventory(ApprovedDocumentProcessInventoryRequest request);
    RequestStakeHolder findRequestStakeHolderByIdRequestStakeHolder(Integer idRequestStakeHolder);
    List<RequestStakeHolder> findRequestStakeHolderByIdRequest(Integer idRequest);
    List<RequestStakeHolderDetails> findRequestStakeHolderDetailsByIdRequest(Integer idRequest);
}
