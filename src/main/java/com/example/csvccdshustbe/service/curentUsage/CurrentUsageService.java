package com.example.csvccdshustbe.service.curentUsage;


import com.example.csvccdshustbe.entity.CurrentUsage;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.currentUsage.CreateCurrentUsageRequest;
import com.example.csvccdshustbe.request.currentUsage.UpdateCurrentUsageRequest;
import com.example.csvccdshustbe.response.curentUsage.FindAllCurrentUsageResponse;


import java.util.List;

public interface CurrentUsageService  {
    List<FindAllCurrentUsageResponse> findAllCurrentUsage();

    void createCurrentUsage(CreateCurrentUsageRequest request) throws ValidateFiledException;

    void updateCurrentUsage(UpdateCurrentUsageRequest request) throws ValidateFiledException;

    void deleteCurrentUsageByIdCU(Integer idCurrentUsage);
}
