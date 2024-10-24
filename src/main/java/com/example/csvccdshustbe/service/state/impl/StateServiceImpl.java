package com.example.csvccdshustbe.service.state.impl;

import com.example.csvccdshustbe.dto.request.RequestDetailsDto;
import com.example.csvccdshustbe.dto.requestData.RequestDataDetailsDto;
import com.example.csvccdshustbe.dto.requestStakeHolder.RequestStakeHolderDetails;
import com.example.csvccdshustbe.dto.state.StateDetailsDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.repository.state.StateRepository;
import com.example.csvccdshustbe.response.request.RequestDetailsResponse;
import com.example.csvccdshustbe.response.requestData.RequestDataDetailsResponse;
import com.example.csvccdshustbe.response.requestStakeHolder.RequestStakeHolderDetailsResponse;
import com.example.csvccdshustbe.response.state.StateDetailsResponse;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.requestData.RequestDataService;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import com.example.csvccdshustbe.service.state.StateService;
import com.example.csvccdshustbe.service.transition.TransitionService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.Constants;
import com.example.csvccdshustbe.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateRepository stateRepository;
    @Autowired
    TransitionService transitionService;
    @Lazy
    @Autowired
    RequestService requestService;
    @Autowired
    RequestDataService requestDataService;
    @Autowired
    RequestStakeHolderService requestStakeHolderService;
    @Lazy
    @Autowired
    ProcessService processService;
    @Autowired
    UserRoleService userRoleService;


    @Override
    public List<State> saveAllState(List<State> stateList) {
        return stateRepository.saveAll(stateList);
    }

    @Override
    public void updateStatusStateByIdState(Integer idState) {
        List<Request> requests = requestService.findAllRequestByIdState(idState);
        Optional<State> state = stateRepository.findStateByIdState(idState);
        if (state.isEmpty()){
            throw new NotFoundException("Don't exits state by id state!");
        }
        updateStatusStateCurrent(state.get(), requests);
    }

    @Override
    public StateDetailsResponse findStateDetailByIdState(Integer idState) {
        List<Integer> idsDepartment =
                ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdsDepartmentCurrent();
        Optional<StateDetailsDto> stateDetailsDto = stateRepository.findStateDetailsByIdState(idState,idsDepartment);
        if (stateDetailsDto.isEmpty()){
            throw new NotFoundException("Don't exits state by id state!");
        }
        List<RequestDetailsDto> requestDetailsDtos = requestService.findRequestDetailsByIdState(stateDetailsDto
                .get()
                .getIdTypeState());
        for (RequestDetailsDto dto : requestDetailsDtos){
            dto.setRequestData(requestDataService.findRequestDataDetailsByIdRequest(dto.getIdRequest()));
            dto.setRequestStakeHolder(requestStakeHolderService.findRequestStakeHolderDetailsByIdRequest(dto.getIdRequest()));
        }
        stateDetailsDto.get().setRequestDetails(requestDetailsDtos);
        return convertToStateDetailsResponse(stateDetailsDto.get());
    }

    private StateDetailsResponse convertToStateDetailsResponse(StateDetailsDto stateDetailsDto) {
        StateDetailsResponse response = new StateDetailsResponse();
        response.setIdState(stateDetailsDto.getIdState());
        response.setStatusState(stateDetailsDto.getStatusState());
        response.setIdTypeState(stateDetailsDto.getIdTypeState());
        response.setCodeTypeState(stateDetailsDto.getCodeTypeState());
        response.setNameTypeState(stateDetailsDto.getNameTypeState());
        response.setTimeCreated(DateUtil.convertStringDateToDate(stateDetailsDto.getTimeCreated(),DateUtil.DATE_FORMAT));
        response.setTimeModified(DateUtil.convertStringDateToDate(stateDetailsDto.getTimeModified(),DateUtil.DATE_FORMAT));
        response.setIdProcess(stateDetailsDto.getIdProcess());
        List<RequestDetailsResponse> request = new ArrayList<>();
        for (RequestDetailsDto dto : stateDetailsDto.getRequestDetails()){
            request.add(convertToRequestDetailResponse(dto));
        }
        return response;
    }

    private RequestDetailsResponse convertToRequestDetailResponse(RequestDetailsDto dto) {
        RequestDetailsResponse response = new RequestDetailsResponse();
        response.setIdRequest(dto.getIdRequest());
        response.setName(dto.getName());
        response.setDescription(dto.getDescription());
        response.setStatus(dto.getStatus());
        response.setTimeCreated(DateUtil.convertStringDateToDate(dto.getTimeCreated(),DateUtil.DATE_FORMAT));
        response.setTimeModified(DateUtil.convertStringDateToDate(dto.getTimeModified(),DateUtil.DATE_FORMAT));
        List<RequestDataDetailsResponse> dataResponse = new ArrayList<>();
        List<RequestStakeHolderDetailsResponse> stakeHolderResponse = new ArrayList<>();
        for (RequestDataDetailsDto dataDetailsDto : dto.getRequestData()){
            dataResponse.add(convertToRequestDataResponse(dataDetailsDto));
        }
        for (RequestStakeHolderDetails stakeHolderDetails : dto.getRequestStakeHolder()){
            stakeHolderResponse.add(convertToRequestStakeHolderResponse(stakeHolderDetails));
        }
        response.setRequestData(dataResponse);
        response.setRequestStakeHolder(stakeHolderResponse);
        return response;
    }

    private RequestStakeHolderDetailsResponse convertToRequestStakeHolderResponse(RequestStakeHolderDetails stakeHolderDetails) {
        RequestStakeHolderDetailsResponse response = new RequestStakeHolderDetailsResponse();
        response.setIdRequestStakeHolder(stakeHolderDetails.getIdRequestStakeHolder());
        response.setUserName(stakeHolderDetails.getUserName());
        response.setFullName(stakeHolderDetails.getFullName());
        response.setStatus(stakeHolderDetails.getStatus());
        response.setTimeCreated(DateUtil.convertStringDateToDate(stakeHolderDetails.getTimeCreated(),DateUtil.DATE_FORMAT));
        response.setTimeModified(DateUtil.convertStringDateToDate(stakeHolderDetails.getTimeModified(),DateUtil.DATE_FORMAT));
        response.setReason(stakeHolderDetails.getReason());
        response.setDescription(stakeHolderDetails.getDescription());
        return response;
    }

    private RequestDataDetailsResponse convertToRequestDataResponse(RequestDataDetailsDto dataDetailsDto) {
        RequestDataDetailsResponse response = new RequestDataDetailsResponse();
        response.setIdRequestData(dataDetailsDto.getIdRequestData());
        response.setName(dataDetailsDto.getName());
        response.setValue(dataDetailsDto.getValue());
        response.setStatus(dataDetailsDto.getStatus());
        return response;
    }

    private void handleStateNext(State stateCurrent) {
        Transition transition = transitionService.findTransitionByIdProcess(stateCurrent.getIdProcess());
        Optional<State> stateNext = stateRepository.findStateByIdProcessAndStepNext(stateCurrent.getIdProcess(),
                stateCurrent.getStep() + 1);
        if(stateNext.isPresent()) {
            transition.setIdStateCurrent(transition.getIdStateNext());
            transition.setIdStateNext(stateNext.get().getIdState());
        } else {
            transition.setIdStateCurrent(transition.getIdStateNext());
        }
        transitionService.saveTransition(transition);
        if (transition.getIdStateCurrent().equals(transition.getIdStateNext())){
            transitionService.updateStatusProcessByIdProcess(transition.getIdProcess(), Constants.STATUS_SUCCESS_PROCESS);
        }
        handleRequest(stateNext.get());
    }


    private void handleRequest(State state) {
        Request request = requestService.createNewRequestProcess(contructionRequest(state));
        requestDataService.createNewRequestData(contructionRequestData(request));
        requestStakeHolderService.createNewRequestStakeHolder(contructionRequestStakeHolder(request,state.getIdProcess()));
    }

    private List<RequestStakeHolder> contructionRequestStakeHolder(Request request, Integer idProcess) {
        Process process = processService.findProcessByIdProcess(idProcess);
        Integer idDepartment = process.getIdDepartment();
        List<UserRole> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(), idDepartment);
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (UserRole userRole : userRoles){
            RequestStakeHolder stakeHolder = new RequestStakeHolder();
            stakeHolder.setIdRequest(request.getIdRequest());
            stakeHolder.setIdUser(userRole.getIdUser());
            stakeHolder.setStatus(Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
            stakeHolder.setTimeCreated(timeCurrent);
            stakeHolder.setTimeModified(timeCurrent);
            stakeHolder.setIdDepartment(userRole.getIdDepartment());
            stakeHolders.add(stakeHolder);
        }
        return stakeHolders;
    }

    private RequestData contructionRequestData(Request request) {
        RequestData requestData = new RequestData();
        requestData.setIdRequest(request.getIdRequest());
        requestData.setStatus(Constants.STATUS_REQUEST_DATA_ACTIVE);
        String timeCurrent = String.valueOf(new Date().getTime());
        requestData.setTimeCreated(timeCurrent);
        requestData.setTimeModified(timeCurrent);
        return requestData;
    }

    private Request contructionRequest(State state) {
        Request request = new Request();
        request.setIdProcess(state.getIdProcess());
        request.setIdState(state.getIdState());
        request.setName("Not real");
        request.setName("Not real");
        request.setStatus(Constants.STATUS_REQUEST_PENDING);
        String timeCurrent = String.valueOf(new Date().getTime());
        request.setTimeCreated(timeCurrent);
        request.setTimeModified(timeCurrent);
        return request;
    }


    private void updateStatusStateCurrent(State state, List<Request> requests) {
        for (Request request: requests){
            if (request.getStatus().equals(Constants.STATUS_REQUEST_PENDING)) {
                return;
            } else if (request.getStatus().equals(Constants.STATUS_REQUEST_FALSE)){
                state.setStatus(Constants.STATUS_STATE_FALSE);
                state.setTimeModified(String.valueOf(new Date().getTime()));
                stateRepository.save(state);
                return;
            }
        }
        state.setStatus(Constants.STATUS_STATE_SUCCESS);
        state.setTimeModified(String.valueOf(new Date().getTime()));
        stateRepository.save(state);
        handleStateNext(state);
    }
}
