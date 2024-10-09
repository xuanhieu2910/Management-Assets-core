package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.roleAllowAssignt.FindRestRoleRequest;
import com.example.csvccdshustbe.request.roleAllowAssignt.UpdateRoleAllowAssignRequest;
import com.example.csvccdshustbe.service.roleAllowAssign.RoleAllowAssignService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Role Allow Assign User Controller", description = "The Role Allow Assign User APIs. Contains operations" +
        " like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/role-allow-assign")
public class RoleAllowAssignController {


    @Autowired
    RoleAllowAssignService roleAllowAssignService;


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllRoleAllowAssign(){
        try {
            return ApiResponseDto.createdWithState(roleAllowAssignService.findAllRoleAllowAssign(),
                    "Find all role allow assign success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update-assign")
    public ResponseEntity<?> updateRoleAllowAssign(@RequestBody List<UpdateRoleAllowAssignRequest> request){
        try {
            roleAllowAssignService.updateRoleAllowAssign(request);
            return ApiResponseDto.createdWithMessage("Update role allow assign success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/find-role-allow-assign")
    public ResponseEntity<?> findRoleAllowAssignByCurrentRole(){
        try {
            return ApiResponseDto.createdWithState(roleAllowAssignService.findAllRoleAllowAssignByTitleRole(),
                    "Find role allow assign success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-rest-role")
    public ResponseEntity<?> findRestRole(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindRestRoleRequest request){
        try {
            return ApiResponseDto.createdWithState(roleAllowAssignService.findRestRoleResponseAssign(request),
                    "Find rest role assign success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
