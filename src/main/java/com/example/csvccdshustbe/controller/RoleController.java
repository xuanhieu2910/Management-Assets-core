package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.role.CreateNewRoleRequest;
import com.example.csvccdshustbe.service.role.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Role Controller", description = "The Role APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/role")
public class RoleController {


    @Autowired
    RoleService roleService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllRoleDefault(){
        try {
            return ApiResponseDto.createdWithState(roleService.findAllRole(),
                    "Find all role default success!",
                    HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewRole(@RequestBody CreateNewRoleRequest request){
        try {
            roleService.createNewRole(request);
            return ApiResponseDto.createdWithMessage("Create role success!", HttpStatus.OK);
        } catch (ValidateFiledException e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }

    }
}
