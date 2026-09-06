package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.taskSendDetailMail.FindAllTaskSendDetailMailRequest;
import com.example.csvccdshustbe.request.taskSendDetailMail.ListTaskSendDetailMailRequest;
import com.example.csvccdshustbe.service.taskSendDetailMail.TaskSendDetailMailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Log4j2
@Tag(name = "Task Send Detail Mail Controller", description = "The Task Send Detail Mail APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/task-send-detail-mail")
public class TaskSendDetailMailController {

    @Autowired
    TaskSendDetailMailService taskSendDetailMailService;


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllTaskSendDetailMail(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllTaskSendDetailMailRequest request){
        try {
            return ApiResponseDto.createdWithState(taskSendDetailMailService.findAllTaskSendDetail(request),
                    "Find all task send detail mail success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteListTaskSendDetailMail(@RequestBody ListTaskSendDetailMailRequest request){
        try {
            taskSendDetailMailService.deleteTaskSendDetailMailByIds(request.getIdsTaskSendDetailMail());
            return ApiResponseDto.createdWithMessage("Delete task send detail mail success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/re-send")
    public ResponseEntity<?> resendMail(@RequestParam String codeTaskSendMail){
        try {
            taskSendDetailMailService.reSendTaskSendDetailMail(codeTaskSendMail);
            return ApiResponseDto.createdWithMessage("Re-send mail success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
