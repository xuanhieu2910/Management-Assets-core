package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.Location.CreateLocationRequest;
import com.example.csvccdshustbe.request.Location.FindAllLocationRequest;
import com.example.csvccdshustbe.request.Location.FindAllLocationVisibleRequest;
import com.example.csvccdshustbe.request.Location.UpdateLocationRequest;
import com.example.csvccdshustbe.request.Location.UpdateVisibleLocationRequest;
import com.example.csvccdshustbe.service.location.LocationService;
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

@Tag(name = "Location Controller", description = "The Location APIs. Contains operations like find all, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/location")
public class LocationController {
    @Autowired
    LocationService locationService;
    @GetMapping("/find-all-visible")
            public ResponseEntity<?> findAllLocationVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllLocationVisibleRequest request){
        try{
            return ApiResponseDto.createdWithState(locationService.findAllLocationVisibleResponse(request),
                    "Find all location success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllLocationVisible(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllLocationRequest request){
        try{
            return ApiResponseDto.createdWithState(locationService.findAllLocationResponse(request),
                    "Find all location success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@RequestBody CreateLocationRequest request){
        try {
            locationService.createLocation(request);
            return ApiResponseDto.createdWithMessage("Create new Location success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateLocation(@RequestBody UpdateLocationRequest request){
        try {
            locationService.updateLocation(request);
            return ApiResponseDto.createdWithMessage("Update Location success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteLocationByIdLocation(@RequestParam("id-location") Integer idLocation){
        try {
            locationService.deleteLocationByIdLocation(idLocation);
            return ApiResponseDto.createdWithMessage("Delete Location success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateVisibleLocation(@RequestBody UpdateVisibleLocationRequest request){
        try {
            locationService.updateVisibleLocation(request);
            return ApiResponseDto.createdWithMessage("Update status location success!", HttpStatus.OK);
        } catch (NotFoundException | ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
