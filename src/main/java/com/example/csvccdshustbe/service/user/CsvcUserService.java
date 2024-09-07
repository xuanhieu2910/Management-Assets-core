package com.example.csvccdshustbe.service.user;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.request.user.FindAllUserUsedRequest;
import com.example.csvccdshustbe.response.user.FindAllUserUsedResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface CsvcUserService extends UserDetailsService {


    CsvcUser findByIdCsvcUser(Integer idUser);

    Boolean exitsByUserName(String userName);

    CsvcUser saveCsvcUser(CsvcUser csvcUser);

    Page<FindAllUserUsedResponse> findAllUserUsedResponse(FindAllUserUsedRequest request);
}
