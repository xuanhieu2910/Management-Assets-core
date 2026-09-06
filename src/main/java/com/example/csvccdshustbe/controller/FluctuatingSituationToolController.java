package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FindAllFluctuatingSituationToolRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FindAllFluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationAsset.FluctuatingSituationAssetRequest;
import com.example.csvccdshustbe.request.fluctuatingSituationTool.FluctuatingSituationToolRequest;
import com.example.csvccdshustbe.service.fluctuatingSituationToolService.FluctuatingSituationToolService;
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
@Tag(name = "Fluctuating Situation Tool Controller", description = "The Fluctuating Situation Tool APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/fluctuating-situation-tool")
public class FluctuatingSituationToolController {

    @Autowired
    FluctuatingSituationToolService fluctuatingSituationToolService;


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllFluctuatingSituationAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllFluctuatingSituationToolRequest request){
        try {
            return ApiResponseDto.createdWithState(
                    fluctuatingSituationToolService.findAllFluctuatingSituationTool(request),
                    "Find all fluctuating situation asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/statistic")
    public ResponseEntity<?> getStatisticFluctuatingSituationTool(@RequestParam("id") Integer idFluctuatingSituation){
        try {
            return ApiResponseDto.createdWithState(
                    fluctuatingSituationToolService.getStatisticFluctuatingSituationTool(idFluctuatingSituation),
                    "Get statistic fluctuating situation tool", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }



    @PostMapping("/update-status-tool")
    public ResponseEntity<?> updateFluctuatingSituationTool(@RequestBody FluctuatingSituationToolRequest request) {
        try {
            fluctuatingSituationToolService.updateStatusToolFluctuatingSituation(request);
            return ApiResponseDto.createdWithMessage("Update status tool success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
