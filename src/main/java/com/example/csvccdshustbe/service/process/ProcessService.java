package com.example.csvccdshustbe.service.process;

import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedRequest;
import com.example.csvccdshustbe.response.process.FindAllProcessBeAssignedResponse;
import org.springframework.data.domain.Page;

public interface ProcessService {

    Process saveProcess(Process process);
    void createIncreaseAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException;
    Process findProcessByIdProcess(Integer idProcess);
    Page<FindAllProcessBeAssignedResponse> findAllProcessBeAssignedResponse(FindAllProcessBeAssignedRequest request);
}
