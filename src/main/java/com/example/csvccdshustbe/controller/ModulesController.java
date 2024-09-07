package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.modules.ModulesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Modules Controller", description = "The Modules APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/modules")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class ModulesController {


    @Autowired
    ModulesService modulesService;

    @GetMapping("/find-all-visible")
    public ResponseEntity<?> findAllModulesVisible(@RequestParam("id-asset-category") Integer idAssetCategory) {
        try {
            return ApiResponseDto.createdWithState(modulesService.findAllModulesByIdAssetCategory(idAssetCategory),
                    "Find all modules success!", HttpStatus.OK);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
