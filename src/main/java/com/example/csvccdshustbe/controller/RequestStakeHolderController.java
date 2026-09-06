package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.requestStakeHolder.ApprovedRequestStakeHolderRequest;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;


@Log4j2
@Tag(name = "Request Stake Holder Controller", description = "The Request Stake Holder APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/request-stake-holder")
public class RequestStakeHolderController {


    @Autowired
    RequestStakeHolderService requestStakeHolderService;


    @PostMapping("/approved-increase")
    public ResponseEntity<?> approvedRequestStakeHolderIncrease(@RequestBody ApprovedRequestStakeHolderRequest request){
        try {
            requestStakeHolderService.approvedRequestStakeHolder(request);
            return ApiResponseDto.createdWithMessage("Approved request stake holder increase success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/approved-change")
    public ResponseEntity<?> approvedRequestStakeHolderChange(@RequestBody ApprovedRequestStakeHolderRequest request){
        try {
            requestStakeHolderService.approvedRequestStakeHolder(request);
            return ApiResponseDto.createdWithMessage("Approved request stake holder change success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/approved-revaluation")
    public ResponseEntity<?> approvedRequestStakeHolderRevaluation(@RequestBody ApprovedRequestStakeHolderRequest request){
        try {
            requestStakeHolderService.approvedRequestStakeHolder(request);
            return ApiResponseDto.createdWithMessage("Approved request stake holder revaluation success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/approved-decrease")
    public ResponseEntity<?> approvedRequestStakeHolderRevaluationDecrease(@RequestBody ApprovedRequestStakeHolderRequest request){
        try {
            requestStakeHolderService.approvedRequestStakeHolder(request);
            return ApiResponseDto.createdWithMessage("Approved request stake holder decrease success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/approved-document-inventory")
    public ResponseEntity<?> approvedRequestStakeHolderRevaluationDocumentInventory(@RequestBody ApprovedRequestStakeHolderRequest request){
        try {
            requestStakeHolderService.approvedRequestStakeHolder(request);
            return ApiResponseDto.createdWithMessage("Approved request stake holder document inventory success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/approved-update-inventory")
    public ResponseEntity<?> approvedRequestStakeHolderRevaluationInventory(@RequestBody ApprovedRequestStakeHolderRequest request){
        try {
            requestStakeHolderService.approvedRequestStakeHolder(request);
            return ApiResponseDto.createdWithMessage("Approved request stake holder inventory success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

//    @PostMapping("/approved-document-inventory")
//    public  ResponseEntity<?> approvedDocumentInventory(@RequestBody ApprovedDocumentProcessInventoryRequest request){
//        try {
//            requestStakeHolderService.approvedInventory(request);
//            return ApiResponseDto.createdWithMessage("Approved inventory success!", HttpStatus.OK);
//        } catch (NotFoundException e) {
//            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
//        }
//    }
}

