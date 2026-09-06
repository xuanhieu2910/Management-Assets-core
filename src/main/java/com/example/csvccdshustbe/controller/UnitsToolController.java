package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.unitsTool.CreateUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.FindAllUnitsToolRequest;
import com.example.csvccdshustbe.request.unitsTool.UpdateUnitsToolRequest;
import com.example.csvccdshustbe.service.unitsTool.UnitsToolService;
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
@Tag(name = "Units Tool Controller", description = "The Units Tool APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/units-tool")
public class UnitsToolController {
    @Autowired
    UnitsToolService unitsToolService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllUnitByCodeNameAssetCategory(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllUnitsToolRequest findAllUnitsToolRequest){
        try {
            return ApiResponseDto.createdWithState(unitsToolService.findAllUnitsTool(findAllUnitsToolRequest),
                    "Find all units tool success", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
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
