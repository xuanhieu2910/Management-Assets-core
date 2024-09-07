package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.declare.DeclareService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Declare Controller", description = "The Declare APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/declare")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class DeclareController {


    @Autowired
    DeclareService declareService;

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllDeclareVisible(@RequestParam("id-asset-category")Integer idAssetCategory){
        try {
            return ApiResponseDto.createdWithState(declareService.findAllDeclareVisibleByIdAssetCategory(idAssetCategory),
                    "Find all declare visible success!", HttpStatus.OK);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
