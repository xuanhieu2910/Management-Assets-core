package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.typeUse.CreateTypeUseRequest;
import com.example.csvccdshustbe.request.typeUse.FindAllTypeUseRequest;
import com.example.csvccdshustbe.request.typeUse.UpdateTypeUseRequest;
import com.example.csvccdshustbe.service.typeUse.TypeUseService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Documents Attack Controller", description = "The Type Use APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/type-use")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class TypeUseController {
    @Autowired
    TypeUseService typeUseService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllTypeUseActive(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllTypeUseRequest findAllTypeUseRequest){
        try {
            return ApiResponseDto.createdWithState(
                    typeUseService.findAllTypeUseActiveResponse(findAllTypeUseRequest),
                    "Find all Type Use success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createTypeUse(@RequestBody CreateTypeUseRequest request){
        try {
            typeUseService.createTypeUse(request);
            return ApiResponseDto.createdWithMessage("Create new TypeUse success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateTypeUse(@RequestBody UpdateTypeUseRequest request){
        try {
            typeUseService.updateTypeUse(request);
            return ApiResponseDto.createdWithMessage("Update TypeUse success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteTypeUseByIdTypeUse(@RequestParam("id-typeuse") Integer idTypeUse){
        try {
            typeUseService.deleteTypeUseByIdTypeUse(idTypeUse);
            return ApiResponseDto.createdWithMessage("Delete TypeUse success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
