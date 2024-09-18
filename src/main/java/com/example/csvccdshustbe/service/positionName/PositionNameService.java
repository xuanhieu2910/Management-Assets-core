package com.example.csvccdshustbe.service.positionName;

import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.positionName.*;
import com.example.csvccdshustbe.response.positionName.FindAllPositionNameResponse;
import com.example.csvccdshustbe.response.positionName.FindAllPositionNameVisibleResponse;
import org.springframework.data.domain.Page;

public interface PositionNameService {
    Page<FindAllPositionNameVisibleResponse> findAllPositionNameVisibleResponse(FindAllPositionNameVisibleRequest request);
    Page<FindAllPositionNameResponse> findAllPositionNameResponse(FindAllPositionNameRequest request);
    void createPositionName(CreatePositionNameRequest request) throws ValidateFiledException;

    void  updatePositionName(UpdatePositionNameRequest request) throws ValidateFiledException;

    void deletePositionNameByIdPositionName(Integer idPositionName) throws ValidateFiledException;

    void updateStatusPositionName(UpdateStatusPositionNameRequest request) throws ValidateFiledException;
}
