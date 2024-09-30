package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.capabilities.CapabilitiesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "Capabilities Controller", description = "The Capabilities APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/capabilities")
public class CapabilitiesController {

    @Autowired
    CapabilitiesService capabilitiesService;


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllCapabilities(){
        try {
            return ApiResponseDto.createdWithState(capabilitiesService.findAllCapabilities(),
                    "Find all capabilities", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
