package com.example.csvccdshustbe.service.process.impl;

import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.process.ProcessRepository;
import com.example.csvccdshustbe.request.process.CreateIncreaseAssetRequest;
import com.example.csvccdshustbe.request.process.document.CreateDocumentRequest;
import com.example.csvccdshustbe.service.dataDocument.DataDocumentService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.requestData.RequestDataService;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import com.example.csvccdshustbe.service.state.StateService;
import com.example.csvccdshustbe.service.transition.TransitionService;
import com.example.csvccdshustbe.service.typeProcessService.TypeProcessService;
import com.example.csvccdshustbe.service.typeState.TypeStateService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    ProcessRepository processRepository;
    @Autowired
    DataDocumentService dataDocumentService;
    @Autowired
    DocumentService documentService;
    @Autowired
    TypeProcessService typeProcessService;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    RequestService requestService;
    @Autowired
    TypeStateService typeStateService;
    @Autowired
    StateService stateService;
    @Autowired
    TransitionService transitionService;
    @Autowired
    RequestDataService requestDataService;
    @Autowired
    RequestStakeHolderService requestStakeHolderService;
    @Autowired
    UserRoleService userRoleService;


    @Override
    public Process saveProcess(Process process) {
        return processRepository.save(process);
    }

    @Override
    public void createIncreaseAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException {
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(contructionDocument(request.getDocument(), process));
        dataDocumentService.createNewDataProcessAsset(request, document);
        List<String> codeTypeStates = Arrays.asList(Constants.CODE_TYPE_STATE_INIT, Constants.CODE_TYPE_STATE_TEST,
                Constants.CODE_TYPE_STATE_APPROVED, Constants.CODE_TYPE_STATE_COMPLETED);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(codeTypeStates);
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST)).findFirst().get().getIdState()));
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, process));

    }

    private void validateCreateNewDocument(CreateDocumentRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeIncrease())
                || StringUtils.isBlank(request.getTimeDocument())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }

    private Document contructionDocument(CreateDocumentRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocument(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeIncrease());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartment(process.getIdDepartment());
        return document;
    }

    @Override
    public Process findProcessByIdProcess(Integer idProcess) {
        Optional<Process> process =  processRepository.findProcessByIdProcess(idProcess);
        if (process.isEmpty()){
            throw new NotFoundException("Don't exits process by id process!");
        }
        return process.get();
    }

    /***
     *
     * HIỆN TẠI ĐANG SET QUYỀN MANAGER ĐƠN VỊ SẼ ĐƯỢC PHÊ DUYỆT -> CẦN CHỈNH SỬA LẠI QUYỀN ĐỂ LẤY RA AI JOIN VỀ QUYỀN
     *
     */

    private List<RequestStakeHolder> constructionRequestStakeHolder(Request processRequest, Process process) {
        Integer idDepartment = process.getIdDepartment();
        List<UserRole> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(), idDepartment);
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (UserRole userRole : userRoles){
            RequestStakeHolder stakeHolder = new RequestStakeHolder();
            stakeHolder.setIdRequest(processRequest.getIdRequest());
            stakeHolder.setIdUser(userRole.getIdUser());
            stakeHolder.setStatus(Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
            stakeHolder.setTimeCreated(timeCurrent);
            stakeHolder.setTimeModified(timeCurrent);
            stakeHolder.setIdDepartment(userRole.getIdDepartment());
            stakeHolders.add(stakeHolder);
        }
        return stakeHolders;
    }

    private RequestData constructionRequestData(Request processRequest) {
        RequestData data = new RequestData();
        data.setIdRequest(processRequest.getIdRequest());
        data.setName("Not real");
        data.setValue("Not real");
        data.setStatus(Constants.STATUS_REQUEST_DATA_ACTIVE);
        String timeCurrent = String.valueOf(new Date().getTime());
        data.setTimeCreated(timeCurrent);
        data.setTimeModified(timeCurrent);
        return data;
    }

    private Request constructionRequest(Process process, Integer idState) {
        Request request = new Request();
        request.setIdProcess(process.getIdProcess());
        request.setIdState(idState);
        request.setName(Constants.NAME_INCREASE_REQUEST);
        request.setDescription(null);
        request.setStatus(Constants.STATUS_REQUEST_PENDING);
        String timeCurrent = String.valueOf(new Date().getTime());
        request.setTimeCreated(timeCurrent);
        request.setTimeModified(timeCurrent);
        return request;
    }

    private Transition constructionTransition(Process process, List<State> states) {
        Transition transition = new Transition();
        transition.setIdProcess(process.getIdProcess());
        for (State state: states){
            if (state.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST)){
                transition.setIdStateCurrent(state.getIdState());
            }
            if (state.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_APPROVED)){
                transition.setIdStateNext(state.getIdState());
            }
        }
        return transition;
    }

    private List<State> constructionStateList(Process process, List<TypeState> typeStates) {
        List<State> stateList = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (TypeState typeState : typeStates){
            State state = new State();
            state.setIdTypeState(typeState.getIdTypeState());
            state.setIdProcess(process.getIdProcess());
            state.setStatus(setStatusCreateNewStatue(typeState));
            state.setTimeCreated(timeCurrent);
            state.setTimeModified(timeCurrent);
            state.setCodeTypeState(typeState.getCode());
            state.setStep(setStepCreateNewState(typeState));
            stateList.add(state);
        }
        return stateList;
    }

    private Integer setStepCreateNewState(TypeState typeState) {
        String codeTypeState = typeState.getCode();
        switch (codeTypeState){
            case Constants.CODE_TYPE_STATE_INIT:
                return Constants.STEP_TYPE_STATE_INIT;
            case Constants.CODE_TYPE_STATE_TEST:
                return Constants.STEP_TYPE_STATE_TEST;
            case Constants.CODE_TYPE_STATE_APPROVED:
                return Constants.STEP_TYPE_STATE_APPROVED;
            case Constants.CODE_TYPE_STATE_COMPLETED:
                return Constants.STEP_TYPE_STATE_COMPLETED;
        }
        return 0;
    }

    private int setStatusCreateNewStatue(TypeState typeState) {
        String codeTypeState = typeState.getCode();
        switch (codeTypeState){
            case Constants.CODE_TYPE_STATE_INIT:
                return Constants.STATUS_STATE_SUCCESS;
            case Constants.CODE_TYPE_STATE_TEST,
                    Constants.CODE_TYPE_STATE_APPROVED,
                    Constants.CODE_TYPE_STATE_COMPLETED:
                return Constants.STATUS_STATE_PENDING;
        }
        return 0;
    }


    private Process constructionProcess(TypeProcess typeProcess) {
        Process process = new Process();
        process.setIdTypeProcess(typeProcess.getIdTypeProcess());
        process.setName(Constants.NAME_INCREASE_PROCESS);
        process.setStatus(Constants.STATUS_PENDING_PROCESS);
        String timeCurrent = String.valueOf(new Date().getTime());
        process.setTimeCreated(timeCurrent);
        process.setTimeModified(timeCurrent);
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idDepartment = csvcUserService.getInformationUser().getIdDepartment();
        process.setIdUserCreated(csvcUser.getIdUser());
        process.setIdUserModified(csvcUser.getIdUser());
        process.setIdDepartment(idDepartment);
        return process;
    }

}
