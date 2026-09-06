package com.example.csvccdshustbe.service.token.refeshToken.impl;

import com.example.csvccdshustbe.config.JwtAuthenticationFilter;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.RefreshToken;
import com.example.csvccdshustbe.exception.TokenException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.refreshToken.RefreshTokenRepository;
import com.example.csvccdshustbe.response.token.resfreshToken.RefreshTokenResponse;
import com.example.csvccdshustbe.service.jwt.JwtTokenService;
import com.example.csvccdshustbe.service.token.refeshToken.RefreshTokenService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.sql.Timestamp;
import java.util.*;

@Log4j2
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${jwt.refresh-token.cookie-name}")
    private String refreshTokenName;

    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    JwtTokenService jwtTokenService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public RefreshToken createRefreshToken(Integer userId) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MILLISECOND, (int) refreshExpiration);
        Timestamp later = new Timestamp(cal.getTime().getTime());
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRevoked(false);
        refreshToken.setIdUser(userId);
        refreshToken.setToken(Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes()));
        refreshToken.setExpireDate(String.valueOf(later.getTime()));
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token == null){
            log.error("Token is null");
            throw new TokenException(null,"Token is null!");
        }
        if (Long.parseLong(token.getExpireDate()) < new Date().getTime()){
            refreshTokenRepository.delete(token);
            throw new TokenException(token.getToken(), "Refresh token was expired. Please make a new authentication request");
        }
        return token;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.empty();
    }

    @Override
    public RefreshTokenResponse generateNewToken(String  refreshTokenRequest) throws ValidateFiledException {
        if (Objects.isNull(refreshTokenRequest)){
            throw new ValidateFiledException("Refresh must not empty!");
        }
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(refreshTokenRequest);
        validateRefreshToken(refreshToken,refreshTokenRequest);
        verifyExpiration(refreshToken.get());
        CsvcUser customUserDetails = csvcUserService.findByIdCsvcUser(refreshToken.get().getIdUser());
        String token = jwtTokenService.generateToken(customUserDetails);
        return RefreshTokenResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.get().getToken())
                .tokenType(JwtAuthenticationFilter.TOKEN_PREFIX)
                .build();
    }

    private void validateRefreshToken(Optional<RefreshToken> refreshToken,String tokenRequest){
        if (refreshToken.isEmpty()){
            throw new TokenException(tokenRequest,"Refresh token does not exist");
        }
    }

    @Override
    public ResponseCookie generateRefreshTokenCookie(String token) {
        return ResponseCookie.from(refreshTokenName, token)
                .path("/")
                .maxAge(refreshExpiration/1000) // 15 days in seconds
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    @Override
    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, refreshTokenName);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return "";
        }
    }

    @Override
    public void deleteByToken(String token) {
        if (token == null){
            log.error("Token is null");
            throw new TokenException(null,"Token is null!");
        }
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        validateRefreshToken(refreshToken,token);
        refreshTokenRepository.delete(refreshToken.get());
    }

    @Override
    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshTokenName, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }
}
