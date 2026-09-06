package com.example.csvccdshustbe.repository.refreshToken;

import com.example.csvccdshustbe.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer>, RefreshTokenRepositoryCustom {
}
