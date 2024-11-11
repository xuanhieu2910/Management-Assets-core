package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Tag(name = "Asset Controller", description = "The Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/process")
public class ProcessTestController {


    @GetMapping
    public ResponseEntity<?> testProcess(){
        try {
            log.info("+++++++++++++++++++++++++++++ THÀNH CÔNG RỒI NÈ ++++++++++++++++++++++++++");
            return ApiResponseDto.createdWithMessage("Test Success!", HttpStatus.OK);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
