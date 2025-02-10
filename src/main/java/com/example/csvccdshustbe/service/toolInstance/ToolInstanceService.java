package com.example.csvccdshustbe.service.toolInstance;

import com.example.csvccdshustbe.entity.ToolInstance;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.toolInstance.CreateToolInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.DeleteToolInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.FindAllToolInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.UpdateToolInstanceRequest;
import com.example.csvccdshustbe.response.toolInstance.FindAllToolInstanceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

public interface ToolInstanceService {
    Page<FindAllToolInstanceResponse> findAllToolInstance(FindAllToolInstanceRequest request);
    void createToolInstance(CreateToolInstanceRequest request) throws ValidateFiledException, JsonProcessingException;
    void updateToolInstance(UpdateToolInstanceRequest request);
    void deleteToolInstance(DeleteToolInstanceRequest request);
}
