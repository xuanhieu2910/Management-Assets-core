package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.process.FindAllProcessAssetRequest;
import com.example.csvccdshustbe.service.dataProcessAsset.DataProcessAssetService;
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

@Tag(name = "Data Process Asset Controller", description = "The Data Process Asset APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/data-process-asset")
public class DataProcessAssetController {
    @Autowired
    DataProcessAssetService dataProcessAssetService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllProcessAsset(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllProcessAssetRequest findAllProcessAssetRequest){
        try {
            return ApiResponseDto.createdWithState(dataProcessAssetService.findAllDataProcessAsset(findAllProcessAssetRequest),
                    "Find all process asset success!", HttpStatus.OK);
        } catch (NotFoundException e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
