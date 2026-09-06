package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.original.FindAllOriginalVisibleRequest;
import com.example.csvccdshustbe.request.originalTool.FindAllOriginalToolRequest;
import com.example.csvccdshustbe.service.originalTool.OriginalToolService;
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

@Tag(name = "Original Tools Controller", description = "The Original Tools APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/original-tool")
public class OriginalToolController {
    @Autowired
    OriginalToolService originalToolService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllOriginalToolResponse(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllOriginalToolRequest request){
        try{
            return ApiResponseDto.createdWithState(originalToolService.findAllOriginalToolResponse(request),
                    "Find all original tool success!", HttpStatus.OK);
        }catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
