package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.service.fluctuatingSituationAssetService.FluctuatingSituationAssetService;
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

import java.util.HashMap;

@Log4j2
@Tag(name = "Fluctuating Situation Asset Controller", description = "The Fluctuating Situation Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/fluctuating-situation-asset")
public class FluctuatingSituationAssetController {

    @Autowired
    FluctuatingSituationAssetService fluctuatingSituationAssetService;


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllFluctuatingSituationAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllFluctuatingSituationAssetRequest request){
        try {
            return ApiResponseDto.createdWithState(fluctuatingSituationAssetService.findAllFluctuatingSituationAsset(request),
                    "Find all fluctuating situation asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/update-status-asset")
    public ResponseEntity<?> updateDeclareAsset(@RequestBody FluctuatingSituationAssetRequest request) {
        try {
            fluctuatingSituationAssetService.updateDeclareAssetFluctuatingSituation(request);
            return ApiResponseDto.createdWithMessage("Update declare asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
