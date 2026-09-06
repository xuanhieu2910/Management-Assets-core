package com.example.csvccdshustbe.controller;


import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.goalsUseGround.CreateGoalsUseGroundRequest;
import com.example.csvccdshustbe.request.goalsUseGround.FindAllGoalsUseGroundRequest;
import com.example.csvccdshustbe.request.goalsUseGround.UpdateGoalsUseGroundRequest;
import com.example.csvccdshustbe.service.goalsUseGround.GoalsUseGroundService;
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

@Tag(name = "Goals Use Ground Controller", description = "The Goals Use Ground APIs. Contains operations like find all, find details, edit, delete etc.")
@RestController
@RequestMapping("/api/v1/goals-use-ground")
public class GoalsUseGroundController {
    @Autowired
    GoalsUseGroundService goalsUseGroundService;
    @GetMapping("/find-all")
    public ResponseEntity<?> FindAllGoalsUseGround(@And({
            @Spec(path = "page", params = "page", spec = Like.class),
            @Spec(path = "size", params = "size", spec = Like.class),
            @Spec(path = "keyword", params = "keyword", spec = Like.class)
    }) FindAllGoalsUseGroundRequest findAllGoalsUseGroundRequest){
        try {
            return ApiResponseDto.createdWithState(goalsUseGroundService.findAllGoalsUseGround(findAllGoalsUseGroundRequest),
                    "find all Goals Use Ground success!", HttpStatus.OK);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createGoalsUseGround(@RequestBody CreateGoalsUseGroundRequest request){
        try {
            goalsUseGroundService.createGoalsUseGround(request);
            return ApiResponseDto.createdWithMessage("Create new goals use ground success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateGoalsUseGround(@RequestBody UpdateGoalsUseGroundRequest request){
        try {
            goalsUseGroundService.updateGoalsUseGround(request);
            return ApiResponseDto.createdWithMessage("Update goals use ground success!", HttpStatus.OK);
        } catch (ValidateFiledException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteMedicineTypeByIdMedicineType(@RequestParam("id-goals-use-ground") Integer idGoalsUseGround){
        try {
            goalsUseGroundService.deleteGoalsUseGroundByIdGoalsUseGround(idGoalsUseGround);
            return ApiResponseDto.createdWithMessage("Delete goals use ground success!", HttpStatus.OK);
        } catch (NotFoundException e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
