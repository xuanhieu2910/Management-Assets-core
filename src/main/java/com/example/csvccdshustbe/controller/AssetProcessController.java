package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.assetProcess.FindAllAssetProcessRequest;
import com.example.csvccdshustbe.request.assetProcess.UpdateAllAssetProcessRequest;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Asset Process Controller", description = "The Asset Process APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/asset-process")
public class AssetProcessController {

    @Autowired
    AssetProcessService assetProcessService;



    @GetMapping("/find-all")
    public ResponseEntity<?> findAllAssetProcess(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllAssetProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(assetProcessService.findAllAssetProcess(request),
                    "Find all data asset document success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
