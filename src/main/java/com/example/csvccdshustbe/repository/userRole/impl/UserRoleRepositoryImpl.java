package com.example.csvccdshustbe.repository.userRole.impl;

import com.example.csvccdshustbe.dto.userRole.DepartmentUserRoleDto;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.repository.userRole.UserRoleRepositoryCustom;
import com.example.csvccdshustbe.response.user.FindAllRolesUserResponse;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.ValueUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRoleRepositoryImpl implements UserRoleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<FindAllRolesUserResponse> findAllRolesUserByCodeUser(String codeUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select role.id_role, role.short_name, userRole.picked " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    inner join role role on userRole.id_role = role.id_role " +
                "where csvcUser.code_user = :codeUser ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeUser", codeUser);
        List<Object[]> result = query.getResultList();
        List<FindAllRolesUserResponse> responses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                FindAllRolesUserResponse response = new FindAllRolesUserResponse();
                response.setIdRole(ValueUtil.getIntegerByObject(obj[0]));
                response.setRole(ValueUtil.getStringByObject(obj[1]));
                response.setPicked(ValueUtil.getIntegerByObject(obj[2]));
                responses.add(response);
            }
        }
        return responses;
    }

    @Override
    public List<UserRole> findUserRoleByCodeUser(String codeUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select userRole.id_user_role, userRole.id_user, userRole.id_role, " +
                "       userRole.id_department, userRole.time_created, userRole.time_modified, " +
                "       userRole.picked " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    inner join role role on userRole.id_role = role.id_role " +
                "where csvcUser.code_user = :codeUser ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeUser", codeUser);
        List<Object[]> result = query.getResultList();
        List<UserRole> userRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                UserRole userRole = new UserRole();
                userRole.setIdUserRole(ValueUtil.getIntegerByObject(obj[0]));
                userRole.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                userRole.setIdRole(ValueUtil.getIntegerByObject(obj[2]));
                userRole.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                userRole.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                userRole.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                userRole.setPicked(ValueUtil.getIntegerByObject(obj[6]));
                userRoles.add(userRole);
            }
        }
        return userRoles;
    }

    @Override
    public DepartmentUserRoleDto getDepartmentUserRoleDtoByCodeUser(String codeUser) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select de.id_department, de.name, userRole.id_user_role " +
                "from csvc_user csvcUser " +
                "    inner join user_role userRole on csvcUser.id_user = userRole.id_user " +
                "    left join department de on userRole.id_department = de.id_department " +
                "where userRole.picked = :isPicked " +
                "and csvcUser.code_user = :codeUser ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("codeUser", codeUser);
        query.setParameter("isPicked", Constants.ROLE_USER_PICKED);
        List<Object[]> result = query.getResultList();
        DepartmentUserRoleDto departmentUserRoleDto = new DepartmentUserRoleDto();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj : result){
                departmentUserRoleDto.setIdDepartment(ValueUtil.getIntegerByObject(obj[0]));
                departmentUserRoleDto.setNameDepartment(ValueUtil.getStringByObject(obj[1]));
                departmentUserRoleDto.setIdUserRole(ValueUtil.getIntegerByObject(obj[2]));
            }
        }
        return departmentUserRoleDto;
    }

    @Override
    public List<UserRole> findUserRoleByIdDepartmentAndIdUser(Integer idDepartment, Integer idUser) {
        StringBuilder sb = new StringBuilder();
        sb.append("select userRole.id_user_role, userRole.id_user, userRole.id_role,     " +
                "         userRole.id_department, userRole.time_created, userRole.time_modified,     " +
                "         userRole.picked     " +
                "from csvc_user csvcUser  " +
                "      inner join user_role userRole on csvcUser.id_user = userRole.id_user     " +
                "      inner join role role on userRole.id_role = role.id_role     " +
                "where csvcUser.id_user = :idUser  " +
                "and userRole.id_department = :idDepartment ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUser", idUser);
        query.setParameter("idDepartment", idDepartment);
        List<Object[]> result = query.getResultList();
        List<UserRole> userRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result){
                UserRole userRole = new UserRole();
                userRole.setIdUserRole(ValueUtil.getIntegerByObject(obj[0]));
                userRole.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                userRole.setIdRole(ValueUtil.getIntegerByObject(obj[2]));
                userRole.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                userRole.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                userRole.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                userRole.setPicked(ValueUtil.getIntegerByObject(obj[6]));
                userRoles.add(userRole);
            }
        }
        return userRoles;
    }

    @Override
    public Optional<UserRole> findUserRoleByIdUserRole(Integer idUserRole) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select id_user_role, id_user, id_role,  " +
                "       id_department, time_created, time_modified, picked " +
                "from user_role userRole " +
                "where userRole.id_user_role = :idUserRole ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("idUserRole", idUserRole);
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)){
            for (Object[] obj:result){
                UserRole userRole = new UserRole();
                userRole.setIdUserRole(ValueUtil.getIntegerByObject(obj[0]));
                userRole.setIdUser(ValueUtil.getIntegerByObject(obj[1]));
                userRole.setIdRole(ValueUtil.getIntegerByObject(obj[2]));
                userRole.setIdDepartment(ValueUtil.getIntegerByObject(obj[3]));
                userRole.setTimeCreated(ValueUtil.getStringByObject(obj[4]));
                userRole.setTimeModified(ValueUtil.getStringByObject(obj[5]));
                userRole.setPicked(ValueUtil.getIntegerByObject(obj[6]));
                return Optional.of(userRole);
            }
        }
        return Optional.empty();
    }
}
