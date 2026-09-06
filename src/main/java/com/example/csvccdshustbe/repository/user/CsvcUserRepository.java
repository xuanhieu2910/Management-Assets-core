package com.example.csvccdshustbe.repository.user;

import com.example.csvccdshustbe.entity.CsvcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CsvcUserRepository extends JpaRepository<CsvcUser, Integer>, CsvcUserRepositoryCustom {
}
