package com.example.csvccdshustbe.repository.requestStakeHolder;

import com.example.csvccdshustbe.dto.requestStakeHolder.RequestStakeHolderDetails;
import com.example.csvccdshustbe.entity.RequestStakeHolder;

import java.util.List;
import java.util.Optional;

public interface RequestStakeHolderRepositoryCustom {

    Optional<RequestStakeHolder> findRequestStakeHolderById(Integer idRequestStakeHolder);
    List<RequestStakeHolder> findRequestStakeHolderByIdRequest(Integer idRequest);
    List<RequestStakeHolderDetails> findRequestStakeHolderDetailsByIdRequest(Integer idRequest);
}
