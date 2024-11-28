package com.example.csvccdshustbe.service.process;

import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.*;
import com.example.csvccdshustbe.response.process.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

public interface ProcessService {

    Process saveProcess(Process process);
    void createIncreaseAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException;
    void createInventoryAsset(CreateInventoryAssetRequest request) throws ValidateFiledException;
    void createDecreaseAsset(CreateDecreaseAssetRequest request) throws ValidateFiledException;
    void createChangeAsset(CreateChangeAssetRequest request) throws ValidateFiledException, JsonProcessingException, IllegalAccessException;
    void createRevaluationAsset(CreateRevaluationAssetRequest request) throws ValidateFiledException, JsonProcessingException, IllegalAccessException;
    Process findProcessByIdProcess(Integer idProcess);
    Process updateProcessByIdProcessAndStatus(Integer idProcess, Integer status);
    Page<FindAllProcessBeAssignedResponse> findAllProcessBeAssignedResponse(FindAllProcessBeAssignedRequest request);
    ProcessStatisticsIncreaseResponse getStatisticIncrease();
    ProcessStatisticsInventoryResponse getStatisticInventory();
    ProcessStatisticsDecreaseResponse getStatisticDecrease();
    ProcessStatisticsChangeResponse getStatisticChange();
    ProcessStatisticsRevaluationResponse getStatisticRevaluation();
}
