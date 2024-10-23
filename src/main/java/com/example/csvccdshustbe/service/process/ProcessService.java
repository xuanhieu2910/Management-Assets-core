package com.example.csvccdshustbe.service.process;

import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;

public interface ProcessService {

    Process createNewProcess(Process process);
    void createIncreaseAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException;

}
