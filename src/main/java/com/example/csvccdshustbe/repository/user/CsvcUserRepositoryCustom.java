package com.example.csvccdshustbe.repository.user;

import com.example.csvccdshustbe.entity.CsvcUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface CsvcUserRepositoryCustom {

    Optional<CsvcUser> loadUserByUsername(String username);

    Optional<CsvcUser> findByIdCsvcUser(Integer idUser);

    Boolean exitsByUserName(String userName);
}
