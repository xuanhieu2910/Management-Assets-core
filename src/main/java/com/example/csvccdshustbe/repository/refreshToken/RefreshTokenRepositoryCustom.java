package com.example.csvccdshustbe.repository.refreshToken;

import com.example.csvccdshustbe.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryCustom {
    Optional<RefreshToken> findByToken(String refreshToken);

}
