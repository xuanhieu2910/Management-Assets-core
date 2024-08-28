package com.example.csvccdshustbe.service.auth;

import com.example.csvccdshustbe.dto.user.UserAuthenticationDto;
import com.example.csvccdshustbe.request.user.UserAuthenticationRequest;
import com.example.csvccdshustbe.request.user.UserRegisterAccountRequest;
import com.example.csvccdshustbe.response.user.UserAuthenticationResponse;

public interface AuthenticationService {

    UserAuthenticationDto register(UserRegisterAccountRequest request) throws Exception;

    UserAuthenticationDto authenticate(UserAuthenticationRequest request) throws Exception;
    UserAuthenticationResponse convertToAuthenticationResponse(UserAuthenticationDto authenticationDto);

}
