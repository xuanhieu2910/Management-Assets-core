package com.example.csvccdshustbe.service.user.impl;

import com.example.csvccdshustbe.dto.user.FindAllUserDto;
import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.dto.userRole.DepartmentUserRoleDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.OAuth2Factory;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.user.CsvcUserRepository;
import com.example.csvccdshustbe.request.user.*;
import com.example.csvccdshustbe.response.user.*;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.role.RoleService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.CodeUserUtil;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.PageUtils;
import com.example.csvccdshustbe.utility.RoleUtils;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

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
    @Autowired
    DepartmentService departmentService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CsvcUser> user = csvcUserRepository.loadUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        if (!user.get().isAccountNonLocked()){
            throw new UsernameNotFoundException("User is locked!");
        }
        setIdsDepartment(user.get());
        return user.get();
    }

    private void setIdsDepartment(CsvcUser csvcUser) {
        DepartmentUserRoleDto departmentUserRoleDto = userRoleService.getDepartmentCurrentUserRoleByCodeUser(csvcUser.getCodeUser());
        List<Integer> idsDepartment = departmentService.findIdsStructureDepartment(departmentUserRoleDto.getIdDepartment());
        csvcUser.setIdsDepartmentCurrent(idsDepartment);
        csvcUser.setIdDepartmentCurrent(departmentUserRoleDto.getIdDepartment());
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
    public List<FindAllUserDto> findIdsUserByUsersName(List<String> usersName) {
        return csvcUserRepository.findUserDtoByListUserName(usersName);
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
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartment(csvcUser.getIdsDepartmentCurrent());
        Page<FindAllUserUsedDto> allUserUsedDtos = csvcUserRepository.findAllUserUsedDto(request, pageable);
        return new PageImpl<>(convertToFindAllUserUsedResponse(allUserUsedDtos.get().collect(Collectors.toList()),
                pageable, allUserUsedDtos.getTotalElements()));
    }

    @Override
    public CsvcUser createNewUser(String userName, String fullName) throws RoleException {
        Role role = userRoleService.findRoleByUserName(RolePattern.User.name());
        CsvcUser csvcUser = createCsvcUserByRegisterAccount(userName,fullName);
        saveCsvcUser(csvcUser);
        csvcUser.setRole(Collections.singleton(role));
        userRoleService.saveUserRole(createUserRoleByRegisterAccount(csvcUser.getIdUser(),role.getIdRole()));
        return csvcUser;
    }

    @Override
    public UserAuthenticationResponse getInformationUser() {
        CsvcUser user = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DepartmentUserRoleDto departmentUserRoleDto = userRoleService.getDepartmentCurrentUserRoleByCodeUser(user.getCodeUser());
        UserAuthenticationResponse response = new UserAuthenticationResponse();
        response.setCodeUser(user.getCodeUser());
        response.setUserName(user.getName());
        response.setRoles(RoleUtils.convertToRoleResponse(user.getRole().stream().toList()));
        response.setFullName(user.getFullName());
        response.setIdDepartment(departmentUserRoleDto.getIdDepartment());
        response.setNameDepartment(departmentUserRoleDto.getNameDepartment());
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
        Set<Capabilities> capabilities = new HashSet<>();
        user.getRole().stream().forEach(role -> capabilities.addAll(role.getCapabilities()));
        boolean isExitsRoleCapability = false;
        for (Capabilities capability : capabilities) {
            if(compareCapability(servletPath, method, capability)){
                isExitsRoleCapability = true;
                break;
            }
        }
        if (!isExitsRoleCapability){
            throw new ServletException("Don't Permission");
        }
    }

    @Override
    public void addNewUser(AddNewUserRequest request) throws ValidateFiledException {
        List<Integer> idsUser = csvcUserRepository.findIdsUserByListUserName(request.getUsers());
        if (idsUser.size() != request.getUsers().size()){
            throw new ValidateFiledException("Don't exits user name!");
        }
        List<Integer> idsDepartment = new ArrayList<>();
        List<Integer> idsRole = new ArrayList<>();
        for (AssignRoleDetailsRequest detailsRequest : request.getRoleAssignDetails()){
            idsDepartment.add(detailsRequest.getIdDepartment());
            idsRole.add(detailsRequest.getIdRole());
        }
        departmentService.findDepartmentByIds(idsDepartment);
        roleService.findRoleByIds(idsRole);
        updateStatusAccountUser(idsUser);
        storeRoleUser(idsUser, request.getRoleAssignDetails());
    }

    @Override
    public Page<FindAllUserResponse> findAllUserResponse(FindAllUserRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsStructureDepartment(request);
        Page<FindAllUserResponse> responses = csvcUserRepository.findAllUser(request, pageable);
        return responses;
    }
    public Page<FindAllUserResponse> findAllUserExistResponse(FindAllUserRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        setIdsStructureDepartment(request);
        Page<FindAllUserResponse> responses = csvcUserRepository.findAllUser(request, pageable);
        Page<FindAllUserResponse> filteredResponses = setValueDuplicateUser(responses);
        return filteredResponses;
    }

    private Page<FindAllUserResponse> setValueDuplicateUser(Page<FindAllUserResponse> responses) {
        Map<String, FindAllUserResponse> uniqueResponsesMap = new LinkedHashMap<>();

        for (FindAllUserResponse response : responses.getContent()) {
            String key = response.getCodeUser();
            if (!uniqueResponsesMap.containsKey(key)) {
                uniqueResponsesMap.put(key, response);
            }
        }

        List<FindAllUserResponse> filteredResponses = new ArrayList<>(uniqueResponsesMap.values());
        return new PageImpl<>(filteredResponses, responses.getPageable(), filteredResponses.size());
    }

    @Override
    public FindDetailsUserResponse findDetailsUserResponse(FindDetailsUserRequest request) {
        setIdsDepartmentFindDetailsRequest(request);
        Optional<FindDetailsUserResponse> response = csvcUserRepository.findDetailsUserResponse(request);
        if (response.isEmpty()){
            throw new NotFoundException("Don't exits user!");
        }
        return response.get();
    }

    @Override
    public void removeUserByDepartmentAndCodeUser(RemoveUserDepartmentRequest request) {
        Optional<CsvcUser> user = csvcUserRepository.findByCodeCsvcUser(request.getCodeUser());
        if (user.isEmpty()){
            throw new NotFoundException("Don't exits user by code user!");
        }
        Department department = departmentService.findDepartmentByIdDepartmentAndStatus(request.getIdDepartment(), Constants.DEPARTMENT_ACTIVE_STATUS);
        userRoleService.removeUserByIdDepartmentAndIdUser(department.getIdDepartment(), user.get().getIdUser());
    }

    @Override
    public Map<String, List<FindAllUserUsedDto>> findAllUserUsedToDownload() {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return csvcUserRepository.findAllUserUsedToDownloadByIdsDepartment(csvcUser.getIdsDepartmentCurrent());
    }

    private void setIdsDepartmentFindDetailsRequest(FindDetailsUserRequest request) {
        Integer department = getInformationUser().getIdDepartment();
        request.setIdsDepartment(departmentService.findIdsStructureDepartment(department));
    }

    private void setIdsStructureDepartment(FindAllUserRequest request) {
        Integer idDepartment = getInformationUser().getIdDepartment();
        request.setIdsDepartment(departmentService.findIdsStructureDepartment(idDepartment));
    }

    private void storeRoleUser(List<Integer> idsUser, List<AssignRoleDetailsRequest> roleAssignDetails) {
        String timeCurrent = String.valueOf(new Date().getTime());
        List<UserRole> userRoles = new ArrayList<>();
        for (Integer idUser : idsUser) {
            for (AssignRoleDetailsRequest roleAssign : roleAssignDetails){
                UserRole userRole = new UserRole();
                userRole.setIdUser(idUser);
                userRole.setIdRole(roleAssign.getIdRole());
                userRole.setIdDepartment(roleAssign.getIdDepartment());
                userRole.setTimeCreated(timeCurrent);
                userRole.setTimeModified(timeCurrent);
                userRole.setPicked(roleAssign.getIsPicked());
                userRoles.add(userRole);
            }
        }
        userRoleService.saveAllUserRole(userRoles);
    }

    private void updateStatusAccountUser(List<Integer> idsUser) {
        csvcUserRepository.updateStatusAccountUserByIds(idsUser, Constants.ACCOUNT_IS_UN_LOCK);
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

    private CsvcUser createCsvcUserByRegisterAccount(String userName, String fullName){
        String timeCurrently = String.valueOf(new Timestamp(new Date().getTime()).getTime());
        CsvcUser csvcUser = new CsvcUser();
        csvcUser.setUserName(userName);
        csvcUser.setPassword(null);
        csvcUser.setTimeCreated(timeCurrently);
        csvcUser.setTimeModified(timeCurrently);
        csvcUser.setIsActived(Constants.ACCOUNT_IS_UN_LOCK);
        csvcUser.setAuth(OAuth2Factory.azure.name());
        csvcUser.setCodeUser(CodeUserUtil.autoGenerateSecureRandomUser(userName));
        csvcUser.setFullName(fullName);
        return csvcUser;
    }

    private UserRole createUserRoleByRegisterAccount(Integer idUser, Integer idRole){
        Department departmentDefault = departmentService.findDepartmentDefault();
        String timeCurrently = String.valueOf(new Timestamp(new Date().getTime()).getTime());
        UserRole userRole = new UserRole();
        userRole.setIdUser(idUser);
        userRole.setIdRole(idRole);
        userRole.setTimeCreated(timeCurrently);
        userRole.setTimeModified(timeCurrently);
        userRole.setIdDepartment(departmentDefault.getIdDepartment());
        userRole.setPicked(Constants.IS_PICKED);
        return userRole;
    }
}
