package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.levelTypeAsset.LevelTypeAssetService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Level type Asset Controller", description = "The Units APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/level-type-asset")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class LevelTypeAssetController {
    @Autowired
    LevelTypeAssetService levelTypeAssetService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        try {
            return ApiResponseDto.createdWithState(
                    levelTypeAssetService.findAllLevelTypeAssetResponseByStatus(Constants.LEVEL_TYPE_ASSET_ACTIVE_STATUS), "Find all Level type asset success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
