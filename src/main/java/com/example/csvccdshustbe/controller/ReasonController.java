package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.reason.FindAllReasonsRequest;
import com.example.csvccdshustbe.service.reason.ReasonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "Reason Controller", description = "The Reason APIs. Contains operations like find all edit, delete etc.")
@RestController
@RequestMapping("/api/v1/reason")
public class ReasonController {
    @Autowired
    ReasonService reasonService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllReasonsResponse(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllReasonsRequest request) {
        try {
            return ApiResponseDto.createdWithState(reasonService.findAllReasonsResponse(request),
                    "Find all reason success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
