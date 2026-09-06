package com.example.csvccdshustbe.repository.userRole;

import com.example.csvccdshustbe.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, UserRoleRepositoryCustom {
}
