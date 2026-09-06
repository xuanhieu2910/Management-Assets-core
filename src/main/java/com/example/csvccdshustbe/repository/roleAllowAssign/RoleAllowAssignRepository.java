package com.example.csvccdshustbe.repository.roleAllowAssign;

import com.example.csvccdshustbe.entity.RoleAllowAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAllowAssignRepository extends JpaRepository<RoleAllowAssign, Integer>,
        RoleAllowAssignRepositoryCustom {
}
