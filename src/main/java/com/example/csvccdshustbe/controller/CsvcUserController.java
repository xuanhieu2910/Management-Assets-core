package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.user.*;
import com.example.csvccdshustbe.response.user.UserAuthenticationResponse;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "CSVC User Controller", description = "The Users APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/user")
public class CsvcUserController {

    @Autowired
    CsvcUserService csvcUserService;

    @GetMapping("/find-all-used")
    public ResponseEntity<?> findAllUserUsed(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllUserUsedRequest findAllUserUsedRequest){
        try {
            return ApiResponseDto.createdWithState(csvcUserService.findAllUserUsedResponse(findAllUserUsedRequest),
                    "Find all user used success!", HttpStatus.OK);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @GetMapping("/information")
    public ResponseEntity<?> findInformation(){
        try {
            UserAuthenticationResponse authenticationDto = csvcUserService.getInformationUser();
            return ResponseEntity.ok(authenticationDto);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?> findAllRoles(){
        try {
            return ApiResponseDto.createdWithState(csvcUserService.findAllRolesUser(),
                    "Find all roles user success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/switch-user")
    public ResponseEntity<?> switchUser(@RequestBody SwitchUserRequest request){
        try {
            csvcUserService.switchRoleUser(request);
            return ApiResponseDto.createdWithMessage("Switch role user success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addNewUser(@RequestBody AddNewUserRequest request){
        try {
            csvcUserService.addNewUser(request);
            return ApiResponseDto.createdWithMessage("Add new user success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllUser(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    })FindAllUserRequest request){
        try {
            return ApiResponseDto.createdWithState(csvcUserService.findAllUserResponse(request),
                    "Find all user success!", HttpStatus.OK);
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> findDetailsUser(@RequestBody FindDetailsUserRequest request) {
        try {
            return ApiResponseDto.createdWithState(csvcUserService.findDetailsUserResponse(request),
                    "Get details user success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeUserByIdDepartmentAndCodeUser(@RequestBody RemoveUserDepartmentRequest request){
        try {
            csvcUserService.removeUserByDepartmentAndCodeUser(request);
            return ApiResponseDto.createdWithMessage("Remove user success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }











































}
