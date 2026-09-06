package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.taskSendDetailMail.TaskSendDetailMailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Tag(name = "Email Tracking Controller", description = "The Email Tracking APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/email")
public class EmailTrackingController {

    @Autowired
    TaskSendDetailMailService taskSendDetailMailService;

    @GetMapping("/image.png")
    public ResponseEntity<?> testProcess(HttpServletRequest request){
        try {
            taskSendDetailMailService.updateTrackingTaskSendDetailMail(request);
            return ApiResponseDto.createdWithMessage("Test Success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
