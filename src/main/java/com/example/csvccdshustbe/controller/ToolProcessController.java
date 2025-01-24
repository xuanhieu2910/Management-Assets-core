package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.toolProcess.FindAllToolProcessRequest;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
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

@Tag(name = "Tool Process Controller", description = "The Tool Process APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/tool-process")
public class ToolProcessController {

    @Autowired
    ToolProcessService toolProcessService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllAssetProcess(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllToolProcessRequest request){
        try {
            return ApiResponseDto.createdWithState(toolProcessService.findAllToolProcess(request),
                    "Find all data tool document success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

//    @PostMapping("/create-new-tool-inventory")
//    public ResponseEntity<?> createNewToolInventory(){
//
//    }

}
