package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.service.process.ProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Process Asset Controller", description = "The Process Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/process-asset")
public class ProcessAssetController {


    @Autowired
    ProcessService processService;


    @PostMapping("/increase")
    public ResponseEntity<?> increaseAsset(@RequestBody CreateIncreaseAssetRequest request){
        try {
            processService.createIncreaseAsset(request);
            return ApiResponseDto.createdWithMessage("Create increase asset success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }



}
