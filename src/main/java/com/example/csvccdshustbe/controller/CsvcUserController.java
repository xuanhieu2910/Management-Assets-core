package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.user.FindAllUserUsedRequest;
import com.example.csvccdshustbe.response.user.UserAuthenticationResponse;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CSVC User Controller", description = "The Users APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
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
}
