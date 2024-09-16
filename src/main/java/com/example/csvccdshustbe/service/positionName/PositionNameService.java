package com.example.csvccdshustbe.service.positionName;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.positionName.CreatePositionNameRequest;
import com.example.csvccdshustbe.request.positionName.FindAllPositionNameRequest;
import com.example.csvccdshustbe.request.positionName.UpdatePositionNameRequest;
import com.example.csvccdshustbe.response.positionName.FindAllPositionNameResponse;
import org.springframework.data.domain.Page;

public interface PositionNameService {
    Page<FindAllPositionNameResponse> findAllPositionNameResponseByName(FindAllPositionNameRequest request);
    void createPositionName(CreatePositionNameRequest request) throws ValidateFiledException;

    void  updatePositionName(UpdatePositionNameRequest request) throws ValidateFiledException;

    void deletePositionNameByIdPositionName(Integer idPositionName) throws ValidateFiledException;
}
