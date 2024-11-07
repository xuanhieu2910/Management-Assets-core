package com.example.csvccdshustbe.service.user;

import com.example.csvccdshustbe.dto.user.FindAllUserDto;
import com.example.csvccdshustbe.dto.user.FindAllUserUsedDto;
import com.example.csvccdshustbe.entity.CsvcUser;
import com.example.csvccdshustbe.exception.RoleException;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.request.user.*;
import com.example.csvccdshustbe.response.user.*;
import jakarta.servlet.ServletException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface CsvcUserService extends UserDetailsService {

    CsvcUser findByIdCsvcUser(Integer idUser);
    Optional<CsvcUser> findByCodeUser(String codeUser);
    Optional<CsvcUser> findByUserName(String userName);
    List<FindAllUserDto> findIdsUserByUsersName(List<String> usersName);
    Boolean exitsByUserName(String userName);
    CsvcUser saveCsvcUser(CsvcUser csvcUser);
    Page<FindAllUserUsedResponse> findAllUserUsedResponse(FindAllUserUsedRequest request);
    CsvcUser createNewUser(String userName, String fullName) throws RoleException;
    UserAuthenticationResponse getInformationUser();
    List<FindAllRolesUserResponse> findAllRolesUser();
    void switchRoleUser(SwitchUserRequest request);
    void hasCapability(String servletPath, String method) throws ServletException;
    void addNewUser(AddNewUserRequest request) throws ValidateFiledException;
    Page<FindAllUserResponse> findAllUserResponse(FindAllUserRequest request);
    FindDetailsUserResponse findDetailsUserResponse(FindDetailsUserRequest request);
    void removeUserByDepartmentAndCodeUser(RemoveUserDepartmentRequest request);
    Map<String, List<FindAllUserUsedDto>> findAllUserUsedToDownload();

    Page<FindAllUserResponse> findAllUserExistResponse(FindAllUserRequest request);
}
