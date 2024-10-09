package com.example.csvccdshustbe.service.goalsUseGround;

import com.example.csvccdshustbe.dto.goalsUseGround.FindAllGoalsUseGroundDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.goalsUseGround.CreateGoalsUseGroundRequest;
import com.example.csvccdshustbe.request.goalsUseGround.FindAllGoalsUseGroundRequest;
import com.example.csvccdshustbe.request.goalsUseGround.UpdateGoalsUseGroundRequest;
import com.example.csvccdshustbe.response.goalsUseGround.FindAllGoalsUseGroundResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoalsUseGroundService {
    Page<FindAllGoalsUseGroundResponse> findAllGoalsUseGround(FindAllGoalsUseGroundRequest findAllGoalsUseGroundRequest);
    void createGoalsUseGround(CreateGoalsUseGroundRequest request) throws ValidateFiledException;

    void updateGoalsUseGround(UpdateGoalsUseGroundRequest request) throws ValidateFiledException;

    void deleteGoalsUseGroundByIdGoalsUseGround(Integer idGoalsUseGround);

    List<FindAllGoalsUseGroundDto> findAllGoalsUseGroundToDownload();
}
