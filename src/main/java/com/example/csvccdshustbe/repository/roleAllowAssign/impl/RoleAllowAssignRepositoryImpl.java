package com.example.csvccdshustbe.repository.roleAllowAssign.impl;

import com.example.csvccdshustbe.repository.roleAllowAssign.RoleAllowAssignRepositoryCustom;
import com.example.csvccdshustbe.response.roleAllowAssign.FindAllRoleAllowAssignResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class RoleAllowAssignRepositoryImpl implements RoleAllowAssignRepositoryCustom {


    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<FindAllRoleAllowAssignResponse> findAllRoleAllowAssign() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        return null;
    }
}
