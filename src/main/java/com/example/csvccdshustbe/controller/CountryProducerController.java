package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.request.countryProducer.FindAllCountryProducerActiveRequest;
import com.example.csvccdshustbe.request.countryProducer.UpdateCountryProducerRequest;
import com.example.csvccdshustbe.request.currentUsage.UpdateCurrentUsageRequest;
import com.example.csvccdshustbe.request.department.FindAllDepartmentVisibleRequest;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@Tag(name = "Country producer Controller", description = "The Country producer APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/country-producer")
public class CountryProducerController {
    @Autowired
    CountryProducerService countryProducerService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllCountryProducerByStatus(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllCountryProducerActiveRequest findAllCountryProducerActiveRequest){
        try {
            return ApiResponseDto.createdWithState(
                    countryProducerService.findAllCountryProducerActiveResponse(findAllCountryProducerActiveRequest),
                    "Find all country producer success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createCountryProducer(@RequestBody CreateCountryProducerRequest request){
        try {
            countryProducerService.createCountryProducer(request);
            return ApiResponseDto.createdWithMessage("Create country producer success!", HttpStatus.OK);
        }catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCountryProducer(@RequestBody UpdateCountryProducerRequest request){
        try {
            countryProducerService.updateCountryProducer(request);
            return ApiResponseDto.createdWithMessage("Update Country producer success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteCountryProducerByIdCP(@RequestParam("id-country-producer") Integer idCurrentUsage){
        try {
            countryProducerService.deleteCountryProducerByIdCP(idCurrentUsage);
            return ApiResponseDto.createdWithMessage("Delete Country producer success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
