package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.tool.CreateNewToolRequest;
import com.example.csvccdshustbe.request.tool.FindAllToolRequest;
import com.example.csvccdshustbe.request.tool.UpdateToolRequest;
import com.example.csvccdshustbe.service.tool.ToolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Tool Controller", description = "The Tool APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/tools")
public class ToolController {

    @Autowired
    ToolService toolService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllTools(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllToolRequest findAllToolRequest){
        try {
            return ApiResponseDto.createdWithState(toolService.findAllToolParentResponse(findAllToolRequest),
                    "Find all tool success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all-children")
    public ResponseEntity<?> findAllToolChildren(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllToolRequest findAllToolRequest){
        try {
            return ApiResponseDto.createdWithState(toolService.findAllToolChildrenResponse(findAllToolRequest),
                    "Find all tool children success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/generate-code")
    public ResponseEntity<?> generateCodeTools(){
        try {
            return ApiResponseDto.createdWithState(toolService.generateCodeTool(),
                    "Generate code tool success!",  HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTool(@RequestBody CreateNewToolRequest createNewToolRequest){
        try {
            toolService.createNewTool(createNewToolRequest);
            return ApiResponseDto.createdWithMessage("Create new tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTool(@RequestBody UpdateToolRequest updateToolRequest) {
        try {
            toolService.updateTool(updateToolRequest);
            return ApiResponseDto.createdWithMessage("Update tool success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTools(@RequestParam("salt") String saltTool){
        try {
            toolService.deleteToolBySalt(saltTool);
            return ApiResponseDto.createdWithMessage("Delete tools success!", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/statistic")
    public ResponseEntity<?> getStatistic(){
        try {
            return ApiResponseDto.createdWithState(toolService.getStatisticTool(),
                    "Get statistic success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
