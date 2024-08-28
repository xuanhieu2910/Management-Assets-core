package com.example.csvccdshustbe.service.user.impl;

import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.repository.user.CsvcUserRepository;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
