package com.example.csvccdshustbe.service.user;

import com.example.csvccdshustbe.entity.CsvcUser;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface CsvcUserService extends UserDetailsService {


    CsvcUser findByIdCsvcUser(Integer idUser);

    Boolean exitsByUserName(String userName);

    CsvcUser saveCsvcUser(CsvcUser csvcUser);
}
