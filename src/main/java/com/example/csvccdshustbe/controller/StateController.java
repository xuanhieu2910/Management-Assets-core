package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.service.state.StateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@Tag(name = "State Controller", description = "The Role APIs. Contains operations like find all, create, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/state")
public class StateController {

    @Autowired
    StateService stateService;

    @GetMapping("/details")
    public ResponseEntity<?> findDetailsStateByIdState(@RequestParam("id-state")Integer idState){
        try {
            return ApiResponseDto.createdWithState(stateService.findStateDetailByIdState(idState),
                    "Find details state success!", HttpStatus.OK);
        } catch (NotFoundException e){
          return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
