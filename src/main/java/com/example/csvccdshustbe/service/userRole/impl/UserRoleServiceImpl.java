package com.example.csvccdshustbe.service.userRole.impl;

import com.example.csvccdshustbe.dto.userRole.DepartmentUserRoleDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.repository.role.RoleRepository;
import com.example.csvccdshustbe.repository.user.CsvcUserRepository;
import com.example.csvccdshustbe.repository.userRole.UserRoleRepository;
import com.example.csvccdshustbe.request.userRole.AddNewRoleDepartmentUserRequest;
import com.example.csvccdshustbe.request.userRole.AddNewUserRoleDetailsRequest;
import com.example.csvccdshustbe.request.userRole.UpdateUserRoleRequest;
import com.example.csvccdshustbe.response.user.FindAllRolesUserResponse;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import javax.management.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    CsvcUserRepository csvcUserRepository;

    @Override
    public Role findRoleByUserName(String title) throws RoleException {
        Optional<Role> role = roleRepository.findByTitleRole(title.trim());
        if (role.isEmpty()){
            throw new RoleException("Not found role by title role: " + title);
        }
        return role.get();
    }

    @Override
    public UserRole saveUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public List<FindAllRolesUserResponse> findAllRolesUserByCodeUser(String codeUser) {
        return userRoleRepository.findAllRolesUserByCodeUser(codeUser);
    }

    @Override
    public List<UserRole> findUserRoleByCodeUser(String codeUser) {
        List<UserRole> userRoles = userRoleRepository.findUserRoleByCodeUser(codeUser);
        if (CollectionUtils.isEmpty(userRoles)){
            throw new NotFoundException("Don't exits user roles by code user");
        }
        return userRoles;
    }

    @Override
    public void saveAllUserRole(List<UserRole> userRoles) {
        userRoleRepository.saveAll(userRoles);
    }

    @Override
    public DepartmentUserRoleDto getDepartmentUserRoleByCodeUser(String codeUser) {
        return userRoleRepository.getDepartmentUserRoleDtoByCodeUser(codeUser);
    }


    @Modifying
    @Transactional
    @Override
    public void removeUserByIdDepartmentAndIdUser(Integer idDepartment, Integer idUser) {
        List<UserRole> userRoles = userRoleRepository.findUserRoleByIdDepartmentAndIdUser(idDepartment, idUser);
        if (CollectionUtils.isEmpty(userRoles)){
            throw new NotFoundException("Don't exits user role by id department and id user!");
        }
        userRoleRepository.deleteAll(userRoles);
    }

    @Override
    public void updateUserRole(UpdateUserRoleRequest request) {
        Optional<UserRole> userRole = userRoleRepository.findUserRoleByIdUserRole(request.getIdUserRole());
        if (userRole.isEmpty()) {
            throw new NotFoundException("Don't exits user role!");
        }
        userRole.get().setIdRole(request.getIdRole());
        userRole.get().setIdDepartment(request.getIdDepartment());
        userRole.get().setTimeModified(String.valueOf(new Date().getTime()));
        userRoleRepository.save(userRole.get());
    }

    @Modifying
    @Transactional
    @Override
    public void deleteUserRole(Integer idUserRole) {
        Optional<UserRole> userRole = userRoleRepository.findUserRoleByIdUserRole(idUserRole);
        if (userRole.isEmpty()) {
            throw new NotFoundException("Don't exits user role!");
        }
        userRoleRepository.delete(userRole.get());
    }

    @Override
    public void createNewUserRole(AddNewRoleDepartmentUserRequest request) {
        Optional<CsvcUser> user = csvcUserRepository.findByCodeCsvcUser(request.getCodeUser());
        if (user.isEmpty()){
            throw new NotFoundException("Don't exits user!");
        }
        storeUserRole(request, user.get());
    }

    private void storeUserRole(AddNewRoleDepartmentUserRequest request, CsvcUser user) {
        List<UserRole> userRoles = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (AddNewUserRoleDetailsRequest roleDepartment : request.getRoleDepartment()){
            UserRole userRole = new UserRole();
            userRole.setIdUser(user.getIdUser());
            userRole.setIdRole(roleDepartment.getIdRole());
            userRole.setIdDepartment(roleDepartment.getIdDepartment());
            userRole.setPicked(Constants.ROLE_USER_UN_PICKED);
            userRole.setTimeCreated(timeCurrent);
            userRole.setTimeModified(timeCurrent);
            userRoles.add(userRole);
        }
        userRoleRepository.saveAll(userRoles);
    }
}
