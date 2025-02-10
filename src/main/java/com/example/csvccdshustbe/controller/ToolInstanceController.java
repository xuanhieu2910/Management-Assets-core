package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.assetInstance.CreateAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.DeleteAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.FindAllAssetInstanceRequest;
import com.example.csvccdshustbe.request.assetInstance.UpdateAssetInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.CreateToolInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.DeleteToolInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.FindAllToolInstanceRequest;
import com.example.csvccdshustbe.request.toolInstance.UpdateToolInstanceRequest;
import com.example.csvccdshustbe.service.toolInstance.ToolInstanceService;
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
@Tag(name = "Tool Instance Controller", description = "The Tool Instance APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/tool-instance")
public class ToolInstanceController {

    @Autowired
    ToolInstanceService toolInstanceService;
    @GetMapping("/find-all")
    public ResponseEntity<?> getToolInstance(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllToolInstanceRequest request){
        try {
            return ApiResponseDto.createdWithState(toolInstanceService.findAllToolInstance(request),
                    "Find all tool instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/create-tools")
    public ResponseEntity<?> createTools(@RequestBody CreateToolInstanceRequest request){
        try {
            toolInstanceService.createToolInstance(request);
            return ApiResponseDto.createdWithMessage("Create tool instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/update-tools-instance")
    public ResponseEntity<?> updateAssetInstance(@RequestBody UpdateToolInstanceRequest request){
        try {
            toolInstanceService.updateToolInstance(request);
            return ApiResponseDto.createdWithMessage("Update tool instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAssetInstance(@RequestBody DeleteToolInstanceRequest request){
        try {
            toolInstanceService.deleteToolInstance(request);
            return ApiResponseDto.createdWithMessage("Update tool instance success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
