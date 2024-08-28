package com.example.csvccdshustbe.service.auth.impl;

import com.example.csvccdshustbe.config.JwtAuthenticationFilter;
import com.example.csvccdshustbe.dto.user.UserAuthenticationDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.enums.OAuth2Factory;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.user.UserAuthenticationRequest;
import com.example.csvccdshustbe.request.user.UserRegisterAccountRequest;
import com.example.csvccdshustbe.response.user.UserAuthenticationResponse;
import com.example.csvccdshustbe.service.auth.AuthenticationService;
import com.example.csvccdshustbe.service.jwt.JwtTokenService;
import com.example.csvccdshustbe.service.role.RoleService;
import com.example.csvccdshustbe.service.token.refeshToken.RefreshTokenService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.CodeUserUtil;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.RoleUtils;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;



@Log4j2
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    RefreshTokenService refreshTokenService;



    @Override
    public UserAuthenticationDto register(UserRegisterAccountRequest request) throws Exception {
        if (csvcUserService.exitsByUserName(request.getUsername())) {
            throw new ValidateFiledException("Don't exit user, please use another user name");
        }
        Role role = userRoleService.findRoleByUserName(RolePattern.USER.name());
        ValueUtil.validateStrongPassword(request.getPassword());
        CsvcUser csvcUser = createCsvcUserByRegisterAccount(request);
        csvcUserService.saveCsvcUser(csvcUser);
        userRoleService.saveUserRole(createUserRoleByRegisterAccount(csvcUser.getIdUser(),role.getIdRole()));
        return convertToAuthenticationDto(csvcUser,role);
    }

    private UserAuthenticationDto convertToAuthenticationDto(CsvcUser csvcUser, Role role ) throws Exception {
        UserAuthenticationDto authenticationDto = new UserAuthenticationDto();
        authenticationDto.setIsActived(csvcUser.getIsActived());
        authenticationDto.setCodeUser(csvcUser.getCodeUser());
        authenticationDto.setRoles(RoleUtils.convertToRoleResponse(List.of(role)));
        authenticationDto.setUserName(csvcUser.getUsername());
        authenticationDto.setTokenType(JwtAuthenticationFilter.TOKEN_PREFIX);
        authenticationDto.setFullName(csvcUser.getFullName());
        return authenticationDto;
    }

    @Override
    public UserAuthenticationDto authenticate(UserAuthenticationRequest request) throws Exception {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CsvcUser user = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Role> role = user.getRole().stream().toList();
        String jwt = jwtTokenService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getIdUser()).getToken();
        UserAuthenticationDto authenticationDto = new UserAuthenticationDto();
        authenticationDto.setAccessToken(jwt);
        authenticationDto.setRoles(RoleUtils.convertToRoleResponse(role));
        authenticationDto.setUserName(user.getUsername());
        authenticationDto.setCodeUser(user.getCodeUser());
        authenticationDto.setRefreshToken(refreshToken);
        authenticationDto.setTokenType(JwtAuthenticationFilter.TOKEN_PREFIX);
        authenticationDto.setIsActived(user.getIsActived());
        authenticationDto.setFullName(user.getFullName());
        return authenticationDto;
    }

    public UserAuthenticationResponse convertToAuthenticationResponse(UserAuthenticationDto authenticationDto) {
        UserAuthenticationResponse response = new UserAuthenticationResponse();
        response.setCodeUser(authenticationDto.getCodeUser());
        response.setUserName(authenticationDto.getUserName());
        response.setRoles(authenticationDto.getRoles());
        response.setFullName(authenticationDto.getFullName());
        return response;
    }

    private CsvcUser createCsvcUserByRegisterAccount(UserRegisterAccountRequest request){
        String timeCurrently = String.valueOf(new Timestamp(new Date().getTime()).getTime());
        CsvcUser csvcUser = new CsvcUser();
        csvcUser.setUserName(request.getUsername().trim());
        csvcUser.setPassword(passwordEncoder.encode(request.getPassword()));
        csvcUser.setTimeCreated(timeCurrently);
        csvcUser.setTimeModified(timeCurrently);
        csvcUser.setIsActived(Constants.ACCOUNT_IS_UN_LOCK);
        csvcUser.setAuth(OAuth2Factory.local.name());
        csvcUser.setCodeUser(CodeUserUtil.autoGenerateSecureRandomUser(request.getUsername().trim()));
        return csvcUser;
    }

    private UserRole createUserRoleByRegisterAccount(Integer idUser, Integer idRole){
        String timeCurrently = String.valueOf(new Timestamp(new Date().getTime()).getTime());
        UserRole userRole = new UserRole();
        userRole.setIdUser(idUser);
        userRole.setIdRole(idRole);
        userRole.setTimeCreated(timeCurrently);
        userRole.setTimeModified(timeCurrently);
        return userRole;
    }
}
