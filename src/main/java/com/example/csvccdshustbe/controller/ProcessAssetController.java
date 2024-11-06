package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.CreateInventoryAssetRequest;
import com.example.csvccdshustbe.request.process.FindAllProcessBeAssignedRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentInventoryAssetRequest;
import com.example.csvccdshustbe.service.process.ProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/inventory")
//    public ResponseEntity<?> inventory(@RequestBody CreateInventoryAssetRequest request){
//        try {
//            processService.createInventoryAsset(request);
//            return ApiResponseDto.createdWithMessage("Create inventory asset success!", HttpStatus.OK);
//        } catch (ValidateFiledException e){
//            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (Exception e){
//            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
//        }
//    }

    @GetMapping("/be-assigned")
    public ResponseEntity<?> findAllProcessBeAssigned(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessBeAssignedRequest request){
        try {
            return ApiResponseDto.createdWithState(processService.findAllProcessBeAssignedResponse(request),
                    "Find all process be assigned success!", HttpStatus.OK);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/statistic-increase")
    public ResponseEntity<?> getStatisticIncrease(){
        try {
            return ApiResponseDto.createdWithState(processService.getStatisticIncrease(),
                    "Get statistic increase success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
