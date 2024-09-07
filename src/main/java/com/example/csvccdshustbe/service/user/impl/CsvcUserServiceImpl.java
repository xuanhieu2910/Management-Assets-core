package com.example.csvccdshustbe.service.user.impl;

import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.user.CsvcUserRepository;
import com.example.csvccdshustbe.request.user.FindAllUserUsedRequest;
import com.example.csvccdshustbe.response.user.FindAllUserUsedResponse;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CsvcUserServiceImpl implements CsvcUserService {


    @Autowired
    CsvcUserRepository csvcUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CsvcUser> user = csvcUserRepository.loadUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        if (!user.get().isAccountNonLocked()){
            throw new UsernameNotFoundException("User is locked!");
        }
        return user.get();
    }

    @Override
    public CsvcUser findByIdCsvcUser(Integer idUser) {
        Optional<CsvcUser> user = csvcUserRepository.findByIdCsvcUser(idUser);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        if (!user.get().isAccountNonLocked()){
            throw new UsernameNotFoundException("User is locked!");
        }
        return user.get();
    }

    @Override
    public Boolean exitsByUserName(String userName) {
        return csvcUserRepository.exitsByUserName(userName.trim());
    }

    @Override
    public CsvcUser saveCsvcUser(CsvcUser csvcUser) {
        return csvcUserRepository.save(csvcUser);
    }

    @Override
    public Page<FindAllUserUsedResponse> findAllUserUsedResponse(FindAllUserUsedRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllUserUsedDto> allUserUsedDtos = csvcUserRepository.findAllUserUsedDto(request, pageable);
        return new PageImpl<>(convertToFindAllUserUsedResponse(allUserUsedDtos.get().collect(Collectors.toList()),
                pageable, allUserUsedDtos.getTotalElements()));
    }

    private List<FindAllUserUsedResponse> convertToFindAllUserUsedResponse(List<FindAllUserUsedDto> collect,
                                                                           Pageable pageable, long totalElements) {
        List<FindAllUserUsedResponse> findAllUserUsedResponses = new ArrayList<>();
        for (FindAllUserUsedDto dto: collect) {
            FindAllUserUsedResponse response = new FindAllUserUsedResponse();
            response.setUserName(dto.getUserName());
            response.setCoderUser(dto.getCodeUser());
            response.setFullName(dto.getFullName());
            findAllUserUsedResponses.add(response);
        }
        return findAllUserUsedResponses;
    }
}
