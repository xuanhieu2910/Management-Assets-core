package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.fluctuatingSituation.FindAllFluctuatingSituationRequest;
import com.example.csvccdshustbe.service.fluctuatingSituationService.FluctuatingSituationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Tag(name = "Fluctuating Situation Controller", description = "The Fluctuating Situation APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/fluctuating-situation")
public class FluctuatingSituationController {


    @Autowired
    FluctuatingSituationService fluctuatingSituationService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllFluctuatingSituation(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllFluctuatingSituationRequest request){
        try {
            return ApiResponseDto.createdWithState(fluctuatingSituationService.findAllFluctuatingSituation(request),
                    "Find all fluctuatingSituation success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/statistic-fluctuating-situation")
    public ResponseEntity<?> getStatisticFluctuatingSituation(@RequestParam("tp") Integer typeFluctuatingSituation){
        try {
            return ApiResponseDto.createdWithState(
                    fluctuatingSituationService.getStatisticFluctuatingSituation(typeFluctuatingSituation),
                    "Get statistic fluctuating situation", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
