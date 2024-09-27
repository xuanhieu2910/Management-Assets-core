package com.example.csvccdshustbe.controller;

import com.example.csvccdshustbe.dto.ApiResponseDto;
import com.example.csvccdshustbe.dto.user.UserAuthenticationDto;
import com.example.csvccdshustbe.request.user.UserAuthenticationRequest;
import com.example.csvccdshustbe.request.user.UserRegisterAccountRequest;
import com.example.csvccdshustbe.response.token.resfreshToken.RefreshTokenResponse;
import com.example.csvccdshustbe.response.user.UserAuthenticationResponse;
import com.example.csvccdshustbe.service.auth.AuthenticationService;
import com.example.csvccdshustbe.service.jwt.JwtTokenService;
import com.example.csvccdshustbe.service.token.refeshToken.RefreshTokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication User", description = "The Authentication User API. Contains operations like register,login, logout, refresh-token etc.")
@RestController
@RequestMapping("/api/v1/user/auth")
public class UserAuthenticationController {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserRegisterAccountRequest request){
        try {
            UserAuthenticationDto authenticationDto = authenticationService.register(request);
            ResponseCookie jwtCookie = jwtTokenService.generateJwtCookie(authenticationDto.getAccessToken());
            ResponseCookie refreshTokenCookie = refreshTokenService.generateRefreshTokenCookie(authenticationDto.getRefreshToken());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                    .body(authenticationService.convertToAuthenticationResponse(authenticationDto));
        } catch (Exception e) {
            return ApiResponseDto.createdWithMessage(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody UserAuthenticationRequest request) {
        try {
            UserAuthenticationDto authenticationDto = authenticationService.authenticate(request);
            ResponseCookie jwtCookie = jwtTokenService.generateJwtCookie(authenticationDto.getAccessToken());
            ResponseCookie refreshTokenCookie = refreshTokenService.generateRefreshTokenCookie(authenticationDto.getRefreshToken());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                    .body(authenticationService.convertToAuthenticationResponse(authenticationDto));
        } catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?>refreshToken(HttpServletRequest request){
        try {
            RefreshTokenResponse response = refreshTokenService.generateNewToken(refreshTokenService.getRefreshTokenFromCookies(request));
            ResponseCookie jwtCookie = jwtTokenService.generateJwtCookie(response.getAccessToken());
            ResponseCookie refreshTokenCookie = refreshTokenService.generateRefreshTokenCookie(response.getRefreshToken());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                    .body("Refresh token success!");
        }catch (Exception e){
            return ApiResponseDto.createdWithMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        String refreshToken = refreshTokenService.getRefreshTokenFromCookies(request);
        if(StringUtils.isNotBlank(refreshToken)) {
            refreshTokenService.deleteByToken(refreshToken.trim());
        }
        ResponseCookie jwtCookie = jwtTokenService.getCleanJwtCookie();
        ResponseCookie refreshTokenCookie = refreshTokenService.getCleanRefreshTokenCookie();
        jwtTokenService.deleteJSessionId();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE,refreshTokenCookie.toString())
                .build();

    }
}
