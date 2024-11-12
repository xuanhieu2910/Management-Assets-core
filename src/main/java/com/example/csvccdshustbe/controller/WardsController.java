package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.request.province.FindAllProvinceRequest;
import com.example.csvccdshustbe.request.wards.FindAllWardsRequest;
import com.example.csvccdshustbe.service.wards.WardsService;
import com.example.csvccdshustbe.utility.EmailUtil;
import com.example.csvccdshustbe.utility.MailDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
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
import org.webjars.NotFoundException;

@Tag(name = "Wards Controller", description = "The Wards APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/wards")
public class WardsController {

    @Autowired
    WardsService wardsService;



    @GetMapping("/find-all")
    public ResponseEntity<?> findAllWardsVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllWardsRequest request){
        try{
            return ApiResponseDto.createdWithState(wardsService.findAllWardsResponse(request),
                    "Find all wards success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/test-send-mail")
    public ResponseEntity<?> testSendMail(){
        try{
            EmailUtil.send();
            return ApiResponseDto.createdWithMessage(
                    "Test send mail success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


}
