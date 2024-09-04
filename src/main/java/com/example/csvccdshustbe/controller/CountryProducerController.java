package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.countryProducer.CreateCountryProducerRequest;
import com.example.csvccdshustbe.service.countryProducer.CountryProducerService;
import com.example.csvccdshustbe.utility.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Country producer Controller", description = "The Country producer APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/country-producer")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class CountryProducerController {
    @Autowired
    CountryProducerService countryProducerService;
    @GetMapping("/find-all")
    public ResponseEntity<?> findAllCountryProducerByStatus(){
        try {
            return ApiResponseDto.createdWithState(
                    countryProducerService.findAllCountryProducerResponseByStatus(Constants.COUNTRY_PRODUCER_ACTIVE_STATUS),
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
}
