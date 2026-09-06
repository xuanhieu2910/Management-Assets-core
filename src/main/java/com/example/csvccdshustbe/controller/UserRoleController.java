package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.userRole.AddNewRoleDepartmentUserRequest;
import com.example.csvccdshustbe.request.userRole.UpdateUserRoleRequest;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "User role Controller", description = "The User role APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/user-role")
public class UserRoleController {


    @Autowired
    UserRoleService userRoleService;

    @PutMapping("/update-user-role")
    public ResponseEntity<?> updateUserRole(@RequestBody UpdateUserRoleRequest request){
        try {
            userRoleService.updateUserRole(request);
            return ApiResponseDto.createdWithMessage("Update user role success!", HttpStatus.OK);
        } catch (NotFoundException e){
           return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserRole(@RequestParam("id-user-role") Integer idUserRole){
        try {
            userRoleService.deleteUserRole(idUserRole);
            return ApiResponseDto.createdWithMessage("Delete user role success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/add-role-department")
    public ResponseEntity<?> addNewRoleDepartmentUser(@RequestBody AddNewRoleDepartmentUserRequest request){
        try {
            userRoleService.createNewUserRole(request);
            return ApiResponseDto.createdWithMessage("Create new user role success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
