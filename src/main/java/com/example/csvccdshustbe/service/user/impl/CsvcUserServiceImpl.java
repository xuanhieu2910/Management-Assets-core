package com.example.csvccdshustbe.service.user.impl;

import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.entity.Capabilities;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.entity.Role;
import com.example.csvccdshustbe.entity.UserRole;
import com.example.csvccdshustbe.enums.OAuth2Factory;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.repository.user.CsvcUserRepository;
import com.example.csvccdshustbe.request.user.FindAllUserUsedRequest;
import com.example.csvccdshustbe.request.user.SwitchUserRequest;
import com.example.csvccdshustbe.request.user.UserRegisterAccountRequest;
import com.example.csvccdshustbe.response.user.FindAllRolesUserResponse;
import com.example.csvccdshustbe.response.user.FindAllUserUsedResponse;
import com.example.csvccdshustbe.response.user.UserAuthenticationResponse;
import com.example.csvccdshustbe.service.role.RoleService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.*;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CsvcUserServiceImpl implements CsvcUserService {


    @Autowired
    CsvcUserRepository csvcUserRepository;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    RoleService roleService;
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
    public Optional<CsvcUser> findByCodeUser(String codeUser) {
        return csvcUserRepository.findByCodeCsvcUser(codeUser);
    }

    @Override
    public Optional<CsvcUser> findByUserName(String userName) {
        return csvcUserRepository.findByUserName(userName);
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

    @Override
    public void createNewUser(String userName) throws RoleException {
        Role role = userRoleService.findRoleByUserName(RolePattern.User.name());
        CsvcUser csvcUser = createCsvcUserByRegisterAccount(userName);
        saveCsvcUser(csvcUser);
        userRoleService.saveUserRole(createUserRoleByRegisterAccount(csvcUser.getIdUser(),role.getIdRole()));
    }

    @Override
    public UserAuthenticationResponse getInformationUser() {
        CsvcUser user = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAuthenticationResponse response = new UserAuthenticationResponse();
        response.setCodeUser(user.getCodeUser());
        response.setUserName(user.getName());
        response.setRoles(RoleUtils.convertToRoleResponse(user.getRole().stream().toList()));
        response.setFullName(user.getFullName());
        return response;
    }

    @Override
    public List<FindAllRolesUserResponse> findAllRolesUser() {
        CsvcUser user = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRoleService.findAllRolesUserByCodeUser(user.getCodeUser());
    }

    @Override
    public void switchRoleUser(SwitchUserRequest request) {
        CsvcUser user = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserRole> userRoles = userRoleService.findUserRoleByCodeUser(user.getCodeUser());
        switchToAnotherRole(userRoles, request);
        userRoleService.saveAllUserRole(userRoles);
    }

    @Override
    public void hasCapability(String servletPath, String method) throws ServletException {
        CsvcUser user =  (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Set<Capabilities> capabilities = new HashSet<>();
//        user.getRole().stream().forEach(role -> capabilities.addAll(role.getCapabilities()));
//        boolean isExitsRoleCapability = false;
//        for (Capabilities capability : capabilities) {
//            if(compareCapability(servletPath, method, capability)){
//                isExitsRoleCapability = true;
//                break;
//            }
//        }
//        if (!isExitsRoleCapability){
//            throw new ServletException("Don't Permission");
//        }
    }

    private boolean compareCapability(String servletPath, String method, Capabilities capabilities) {
        String roleCapability = capabilities.getName().substring(capabilities.getName().indexOf(Constants.PATTERN_ROLE_SEPARATE) + 1);
        return servletPath.equals(roleCapability) && method.equals(capabilities.getCapType());
    }

    private void switchToAnotherRole(List<UserRole> userRoles, SwitchUserRequest request) {
        for (UserRole userRole: userRoles){
            if (userRole.getIdRole().equals(request.getIdRoleSwitch())){
                userRole.setPicked(Constants.ROLE_USER_PICKED);
            } else {
                userRole.setPicked(Constants.ROLE_USER_UN_PICKED);
            }
        }
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

    private CsvcUser createCsvcUserByRegisterAccount(String userName){
        String timeCurrently = String.valueOf(new Timestamp(new Date().getTime()).getTime());
        CsvcUser csvcUser = new CsvcUser();
        csvcUser.setUserName(userName);
        csvcUser.setPassword(null);
        csvcUser.setTimeCreated(timeCurrently);
        csvcUser.setTimeModified(timeCurrently);
        csvcUser.setIsActived(Constants.ACCOUNT_IS_UN_LOCK);
        csvcUser.setAuth(OAuth2Factory.azure.name());
        csvcUser.setCodeUser(CodeUserUtil.autoGenerateSecureRandomUser(userName));
        return csvcUser;
    }

    private UserRole createUserRoleByRegisterAccount(Integer idUser, Integer idRole){
        String timeCurrently = String.valueOf(new Timestamp(new Date().getTime()).getTime());
        UserRole userRole = new UserRole();
        userRole.setIdUser(idUser);
        userRole.setIdRole(idRole);
        userRole.setTimeCreated(timeCurrently);
        userRole.setTimeModified(timeCurrently);
        return userRole;
    }
}
