package com.example.csvccdshustbe.repository.process;


import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedRequest;
import com.example.csvccdshustbe.response.process.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProcessRepositoryCustom {
    Optional<Process> findProcessByIdProcess(Integer idProcess);
    Page<FindAllProcessBeAssignedResponse> findAllProcessBeAssigned(FindAllProcessBeAssignedRequest request, Pageable pageable);
    ProcessStatisticsIncreaseResponse getStatisticsIncrease();
    ProcessStatisticsInventoryResponse getStatisticsInventory();
    ProcessStatisticsDecreaseResponse getStatisticsDecrease();
    ProcessStatisticsChangeResponse getStatisticsChange();
    ProcessStatisticsRevaluationResponse getStatisticsRevaluation();
}
