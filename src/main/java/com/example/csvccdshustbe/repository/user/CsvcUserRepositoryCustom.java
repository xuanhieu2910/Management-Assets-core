package com.example.csvccdshustbe.repository.user;

import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.request.user.FindAllUserUsedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface CsvcUserRepositoryCustom {

    Optional<CsvcUser> loadUserByUsername(String username);

    Optional<CsvcUser> findByIdCsvcUser(Integer idUser);
    Optional<CsvcUser> findByCodeCsvcUser(String codeUser);

    Boolean exitsByUserName(String userName);

    Page<FindAllUserUsedDto> findAllUserUsedDto(FindAllUserUsedRequest request, Pageable pageable);

    Optional<CsvcUser> findByUserName(String userName);

    List<Integer> findIdsUserByListUserName(List<String> userName);

    void updateStatusAccountUserByIds(List<Integer> idsUser, Integer status);
}
