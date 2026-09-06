package com.example.csvccdshustbe.service.goalsUseGround.impl;

import com.example.csvccdshustbe.dto.goalsUseGround.FindAllGoalsUseGroundDto;
import com.example.csvccdshustbe.entity.GoalsUseGround;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.goalsUseGround.GoalsUseGroundRepository;
import com.example.csvccdshustbe.request.goalsUseGround.CreateGoalsUseGroundRequest;
import com.example.csvccdshustbe.request.goalsUseGround.FindAllGoalsUseGroundRequest;
import com.example.csvccdshustbe.request.goalsUseGround.UpdateGoalsUseGroundRequest;
import com.example.csvccdshustbe.response.goalsUseGround.FindAllGoalsUseGroundResponse;
import com.example.csvccdshustbe.service.goalsUseGround.GoalsUseGroundService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GoalsUseGroundServiceImpl implements GoalsUseGroundService {
    @Autowired
    GoalsUseGroundRepository goalsUseGroundRepository;
    @Override
    public  Page<FindAllGoalsUseGroundResponse> findAllGoalsUseGround(FindAllGoalsUseGroundRequest findAllGoalsUseGroundRequest) {
        Pageable pageable = PageUtils.buildPage(findAllGoalsUseGroundRequest.getPage(), findAllGoalsUseGroundRequest.getSize());
        Page<GoalsUseGround> goalsUseGrounds = goalsUseGroundRepository.findAllGoalsUseGroundActive(findAllGoalsUseGroundRequest, pageable);
        return new PageImpl<>(convertToFindAllGoalsUseGroundResponse(goalsUseGrounds.stream().toList()),
                pageable, goalsUseGrounds.getTotalElements());
    }



    private List<FindAllGoalsUseGroundResponse> convertToFindAllGoalsUseGroundResponse(List<GoalsUseGround> goalsUseGrounds) {
        List<FindAllGoalsUseGroundResponse> responses = new ArrayList<>();
        for (GoalsUseGround goalsUseGround : goalsUseGrounds){
            FindAllGoalsUseGroundResponse response = new FindAllGoalsUseGroundResponse();
            response.setIdGoalsUseGround(goalsUseGround.getIdGoalsUseGround());
            response.setName(goalsUseGround.getName());
            response.setCode(goalsUseGround.getCode());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public void createGoalsUseGround(CreateGoalsUseGroundRequest request) throws ValidateFiledException {
        validateDataCreateGoalsUseGround(request);
        goalsUseGroundRepository.save(contructGoalsUseGround(request));
    }

    private GoalsUseGround contructGoalsUseGround(CreateGoalsUseGroundRequest request) {
        GoalsUseGround goalsUseGround = new GoalsUseGround();
        goalsUseGround.setName(request.getName().trim());

        if (StringUtils.isNotBlank(request.getCode())){
            goalsUseGround.setCode(request.getCode());
        }
        goalsUseGround.setStatus(request.getStatus());
        String timeCurrent = String.valueOf(new Date().getTime());
        goalsUseGround.setTimeCreated(timeCurrent);
        goalsUseGround.setTimeModified(timeCurrent);
        return goalsUseGround;
    }

    private void validateDataCreateGoalsUseGround(CreateGoalsUseGroundRequest request) throws ValidateFiledException{
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        Optional<GoalsUseGround> goalsUseGround = goalsUseGroundRepository.findGoalsUseGroundByName(request.getName());
        if (goalsUseGround.isPresent()){
            throw new ValidateFiledException("Exits Goals Use Ground by name!");
        }
        if (StringUtils.isNotBlank(request.getCode())) {
            if (request.getCode().equals(goalsUseGround.get().getCode())){
                throw new ValidateFiledException("Exits Goals Use Ground by code");
            }
        }
    }

    @Override
    public void updateGoalsUseGround(UpdateGoalsUseGroundRequest request) throws ValidateFiledException {
        GoalsUseGround goalsUseGround = validateDataUpdateGoalsUseGround(request);
        goalsUseGroundRepository.save(editGoalsUseGround(goalsUseGround, request));
    }

    private GoalsUseGround editGoalsUseGround(GoalsUseGround goalsUseGround, UpdateGoalsUseGroundRequest request) {
        goalsUseGround.setName(request.getName());
        goalsUseGround.setCode(request.getCode());
        goalsUseGround.setStatus(request.getStatus());
        String timeModified = String.valueOf(new Date().getTime());
        goalsUseGround.setTimeModified(timeModified);
        return goalsUseGround;
    }

    private GoalsUseGround validateDataUpdateGoalsUseGround(UpdateGoalsUseGroundRequest request)throws ValidateFiledException{
        Optional<GoalsUseGround> goalsUseGroundOptional = goalsUseGroundRepository.findGoalsUseGroundById(request.getIdGoalsUseGround());
        if (goalsUseGroundOptional.isEmpty()) {
            throw new NotFoundException("Don't exits Goals Use Ground by id!");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new ValidateFiledException("Validate data request!");
        }
        return goalsUseGroundOptional.get();
    }

    @Override
    public void deleteGoalsUseGroundByIdGoalsUseGround(Integer idGoalsUseGround) {
        Optional<GoalsUseGround> goalsUseGroundOptional = goalsUseGroundRepository.findGoalsUseGroundById(idGoalsUseGround);
        if (goalsUseGroundOptional.isEmpty()){
            throw new NotFoundException("Don't exits goals use ground by id!");
        }
        goalsUseGroundRepository.delete(goalsUseGroundOptional.get());
    }

    @Override
    public List<FindAllGoalsUseGroundDto> findAllGoalsUseGroundToDownload() {
        return goalsUseGroundRepository.findAllGoalsUseGroundToDownload();
    }

}
