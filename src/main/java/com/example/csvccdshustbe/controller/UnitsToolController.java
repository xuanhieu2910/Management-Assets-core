package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.units.CreateUnitsRequest;
import com.example.csvccdshustbe.request.units.UpdateUnitsRequest;
import com.example.csvccdshustbe.request.unitsTool.CreateUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.UpdateUnitsToolRequest;
import com.example.csvccdshustbe.service.unitsTool.UnitsToolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Log4j2
@Tag(name = "Units Tool Controller", description = "The Units Tool APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/units-tool")
public class UnitsToolController {
    @Autowired
    UnitsToolService unitsToolService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        try {
            return ApiResponseDto.createdWithState(unitsToolService.findAllUnitsTool(),
                    "Find all units tool success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUnitsTool(@RequestBody CreateUnitsToolRequest request){
        try {
            unitsToolService.createUnitsTool(request);
            return ApiResponseDto.createdWithMessage("Create new Unit Tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUnitsTool(@RequestBody UpdateUnitsToolRequest request){
        try {
            unitsToolService.updateUnitsTool(request);
            return ApiResponseDto.createdWithMessage("Update Unit Tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUnitsToolByIdUnitsTool(@RequestParam("id-unit-tool") Integer idUnitTool){
        try {
            unitsToolService.deleteUnitsToolByIdUnitsTool(idUnitTool);
            return ApiResponseDto.createdWithMessage("Delete Unit Tool success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping
    public ResponseEntity<?> findDetailsAssetCategoryByCodeParentCategory(@RequestParam("id-unit-tool") Integer idUnitTool,@RequestParam("status") Integer status){
        try {
            return ApiResponseDto.createdWithState(
                    unitsToolService.findUnitsByIdUnitToolAndStatus(idUnitTool,status),
                    "Find unit tool details success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
