package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.typeBuyAsset.FindAllTypeBuyAssetPickedRequest;
import com.example.csvccdshustbe.request.typeProcess.FindAllTypeProcessRequest;
import com.example.csvccdshustbe.service.original.typeBuyAsset.TypeBuyAssetService;
import com.example.csvccdshustbe.service.typeProcessService.TypeProcessService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "Type Process Controller", description = "The Type Process APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/type-process")
public class TypeProcessController {

    @Autowired
    TypeProcessService typeProcessService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllTypeProcess(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllTypeProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(typeProcessService.findAllTypeProcess(request),
                    "Find all type buy asset success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
