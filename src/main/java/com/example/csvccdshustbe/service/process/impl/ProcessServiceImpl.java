package com.example.csvccdshustbe.service.process.impl;

import com.example.csvccdshustbe.dto.assetProcess.AssetProcessDto;
import com.example.csvccdshustbe.dto.user.FindAllUserDto;
import com.example.csvccdshustbe.dto.userRole.UserRoleDto;
import com.example.csvccdshustbe.entity.Process;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.enums.RolePattern;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.repository.process.ProcessRepository;
import com.example.csvccdshustbe.repository.tool.ToolRepository;
import com.example.csvccdshustbe.request.document.tool.FindAllToolBeAssignedDocumentToolRequest;
import com.example.csvccdshustbe.request.process.*;
import com.example.csvccdshustbe.request.process.asset.AssetDetailDecreaseRequest;
import com.example.csvccdshustbe.request.process.asset.AssetDetailIncreaseRequest;
import com.example.csvccdshustbe.request.process.asset.AssetDetailInventoryRequest;
import com.example.csvccdshustbe.request.process.councilInventory.CreateCouncilDecreaseRequest;
import com.example.csvccdshustbe.request.process.councilInventory.CreateCouncilInventoryRequest;
import com.example.csvccdshustbe.request.process.document.*;
import com.example.csvccdshustbe.request.process.tool.*;
import com.example.csvccdshustbe.response.document.tool.FindAllDocumentToolBeAssignedDocumentInventoryResponse;
import com.example.csvccdshustbe.response.process.*;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.assetProcess.AssetProcessService;
import com.example.csvccdshustbe.service.document.DocumentService;
import com.example.csvccdshustbe.service.process.ProcessService;
import com.example.csvccdshustbe.service.request.RequestService;
import com.example.csvccdshustbe.service.requestData.RequestDataService;
import com.example.csvccdshustbe.service.requestStakeHolder.RequestStakeHolderService;
import com.example.csvccdshustbe.service.state.StateService;
import com.example.csvccdshustbe.service.taskSendMail.TaskSendMailService;
import com.example.csvccdshustbe.service.tool.ToolService;
import com.example.csvccdshustbe.service.toolProcess.ToolProcessService;
import com.example.csvccdshustbe.service.transition.TransitionService;
import com.example.csvccdshustbe.service.typeProcessService.TypeProcessService;
import com.example.csvccdshustbe.service.typeState.TypeStateService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.service.userRole.UserRoleService;
import com.example.csvccdshustbe.utility.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    ProcessRepository processRepository;
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
    @Autowired
    TaskSendMailService taskSendMailService;
    @Autowired
    AssetProcessService assetProcessService;
    @Autowired
    ToolProcessService toolProcessService;
    @Autowired
    AssetService assetService;
    @Autowired
    ToolService toolService;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    ToolRepository toolRepository;

    @Override
    public Process saveProcess(Process process) {
        return processRepository.save(process);
    }

    @Override
    public void createIncreaseAsset(CreateIncreaseAssetRequest request) throws ValidateFiledException {
        List<Integer> idsAsset = new ArrayList<>();
        request.getAssetDetail().forEach(x->idsAsset.add(x.getIdAsset()));
        validateAssetProcessIncrease(idsAsset);
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(constructionDocumentIncrease(request.getDocument(), process));
        assetProcessService.saveListAssetProcess(contructionAssetProcessIncrease(request.getAssetDetail(), process));
        updateInformationProcessCurrentAsset(idsAsset, process);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(Constants.CODE_TYPE_STATE_INIT,
                Constants.CODE_TYPE_STATE_TEST_APPROVED,
                Constants.CODE_TYPE_STATE_COMPLETED));
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        Integer idDepartment = process.getIdDepartment();
        List<UserRoleDto> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(),
                idDepartment);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, userRoles));
        createTaskSendMailIncrease(userRoles, document, process);
    }


    @Transactional
    @Override
    public void createIncreaseTool(CreateIncreaseToolRequest request) throws ValidateFiledException {
        List<Integer> idsTool = new ArrayList<>();
        request.getToolsDetail().forEach(x->idsTool.add(x.getIdTool()));
        validateToolIncreaseProcess(idsTool);
        List<Tool> tools = toolService.findAllToolByIdsTool(idsTool);
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(constructionDocumentIncrease(request.getDocument(), process));
        toolProcessService.saveAllToolProcess(constructionToolProcessIncrease(request.getToolsDetail(), process));
        updateInformationIncreaseTools(tools, process, request.getToolsDetail());
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED));
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        Integer idDepartment = process.getIdDepartment();
        List<UserRoleDto> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(),
                idDepartment);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, userRoles));
        createTaskSendMailIncrease(userRoles, document, process);
    }

    @Override
    public void createDecreaseTool(CreateDecreaseToolRequest request) throws ValidateFiledException {
        List<Integer> idsTool = new ArrayList<>();
        request.getToolsDetail().forEach(x->idsTool.add(x.getIdTool()));
        validateToolDecreaseProcess(idsTool);
        List<Tool> tools = toolService.findAllToolByIdsTool(idsTool);
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(constructionDocumentDecrease(request.getDocument(), process));
        toolProcessService.saveAllToolProcess(constructionToolProcessDecrease(request.getToolsDetail(), process));
        updateInformationDecreaseTools(tools, process, request.getToolsDetail());
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED));
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        Integer idDepartment = process.getIdDepartment();
        List<UserRoleDto> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(),
                idDepartment);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, userRoles));
        createTaskSendMailIncrease(userRoles, document, process);
    }

    @Override
    public void createDocumentInventoryTool(CreateInventoryToolRequest request) throws ValidateFiledException {
        List<Integer> idsTool = new ArrayList<>();
        request.getToolsDetail().forEach(x->idsTool.add(x.getIdTool()));
        validateToolInventoryProcess(idsTool);
        List<Tool> tools = toolService.findAllToolByIdsTool(idsTool);
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(constructionDocumentInventory(request.getDocument(), process));
        toolProcessService.saveAllToolProcess(constructionToolProcessInventory(request.getToolsDetail(), process));
        updateInformationInventoryTools(tools, process, request.getToolsDetail());
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED));
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        Integer idDepartment = process.getIdDepartment();
        List<UserRoleDto> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(),
                idDepartment);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, userRoles));
        createTaskSendMailIncrease(userRoles, document, process);
    }


    private void updateInformationIncreaseTools(List<Tool> tools, Process process, List<ToolDetailIncreaseRequest> toolsDetailIncrease) {
        for (ToolDetailIncreaseRequest toolDetailIncreaseRequest: toolsDetailIncrease){
            tools.stream().filter(x->x.getIdTool()
                    .equals(toolDetailIncreaseRequest.getIdTool()))
                    .findFirst().ifPresent(x->{
                    x.setQuantityIncreaseCurrent(toolDetailIncreaseRequest.getQuantityIncrease());
                    x.setIdProcessCurrent(process.getIdProcess());
                    x.setIdTypeProcessCurrent(process.getIdTypeProcess());
                    x.setStatusProcessCurrent(process.getStatus());
            });
        }
        toolRepository.saveAll(tools);
    }

    private void updateInformationDecreaseTools(List<Tool> tools, Process process, List<ToolDetailDecreaseRequest> toolsDetailDecrease) {
        for (ToolDetailDecreaseRequest toolDetailDecreaseRequest: toolsDetailDecrease){
            tools.stream().filter(x->x.getIdTool()
                            .equals(toolDetailDecreaseRequest.getIdTool()))
                    .findFirst().ifPresent(x->{
                        x.setQuantityDecreaseCurrent( (x.getQuantityDecreaseCurrent() == 0 ? 0 : x.getQuantityDecreaseCurrent())
                                + toolDetailDecreaseRequest.getQuantityDecrease());
                        x.setIdProcessCurrent(process.getIdProcess());
                        x.setIdTypeProcessCurrent(process.getIdTypeProcess());
                        x.setStatusProcessCurrent(process.getStatus());
                    });
        }
        toolRepository.saveAll(tools);
    }

    private void updateInformationInventoryTools(List<Tool> tools, Process process, List<ToolDetailInventoryRequest> toolsDetailInventory) {
        for (ToolDetailInventoryRequest toolDetailDecreaseRequest: toolsDetailInventory){
            tools.stream().filter(x->x.getIdTool()
                            .equals(toolDetailDecreaseRequest.getIdTool()))
                    .findFirst().ifPresent(x->{
                        x.setIdProcessCurrent(process.getIdProcess());
                        x.setIdTypeProcessCurrent(process.getIdTypeProcess());
                        x.setStatusProcessCurrent(process.getStatus());
                    });
        }
        toolRepository.saveAll(tools);
    }

    private void updateInformationProcessCurrentAsset(List<Integer> idsAsset, Process process) {
        assetService.updateInformationProcessCurrentAsset(idsAsset, process);
    }
    private void updateInformationProcessCurrentTool(List<Integer> idsTool, Process process) {
        toolService.updateInformationProcessCurrentTool(idsTool, process);
    }
    private void validateAssetProcessIncrease(List<Integer> idsAsset) throws ValidateFiledException {
        Integer count = assetService.countAssetIncreasedNotDecreasedOrNotPending(idsAsset);
        if (count != null && count > 0){
            throw new ValidateFiledException("Validate data to increase asset!");
        }
    }

    private void validateToolIncreaseProcess(List<Integer> idsTool) throws ValidateFiledException {
        Integer count = toolService.countToolIncreasedNotDecreased(idsTool);
        if (count != null && count > 0){
            throw new ValidateFiledException("Validate data to increase tool!");
        }
    }

    private void validateToolDecreaseProcess(List<Integer> idsTool) throws ValidateFiledException {
        Integer count = toolService.countToolIsNotIncreaseOrIsDecreaseOrPendingByIdsTool(idsTool);
        if (count != null && count > 0){
            throw new ValidateFiledException("Validate data to decrease tool!");
        }
    }

    private void validateToolInventoryProcess(List<Integer> idsTool) throws ValidateFiledException {
        Integer count = toolService.countToolIsNotIncreaseOrIsDecreaseOrPendingByIdsTool(idsTool);
        if (count != null && count > 0){
            throw new ValidateFiledException("Validate data to decrease tool!");
        }
    }

    private List<AssetProcess> contructionAssetProcessIncrease(List<AssetDetailIncreaseRequest> assetProcessValue, Process process) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (AssetDetailIncreaseRequest assetProcessRequest: assetProcessValue){
            AssetProcess assetProcess = new AssetProcess();
            assetProcess.setIdAsset(assetProcessRequest.getIdAsset());
            assetProcess.setIdProcess(process.getIdProcess());
            assetProcess.setIdTypeProcess(process.getIdTypeProcess());
            assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
            assetProcess.setValue(assetProcessRequest.getValue());
            assetProcess.setTimeCreated(currentTime);
            assetProcess.setTimeModified(currentTime);
            assetProcess.setIdUserCreated(csvcUser.getIdUser());
            assetProcess.setIdUserModified(csvcUser.getIdUser());
            assetProcessList.add(assetProcess);
        }
        return assetProcessList;
    }


    private List<ToolProcess> constructionToolProcessIncrease(List<ToolDetailIncreaseRequest> toolProcessValue, Process process) {
        List<ToolProcess> toolProcessList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (ToolDetailIncreaseRequest toolProcessRequest: toolProcessValue){
            ToolProcess toolProcess = new ToolProcess();
            toolProcess.setIdTool(toolProcessRequest.getIdTool());
            toolProcess.setIdProcess(process.getIdProcess());
            toolProcess.setIdTypeProcess(process.getIdTypeProcess());
            toolProcess.setStatus(Constants.STATUS_TOOL_PROCESS_ACTIVE);
            toolProcess.setValue(toolProcessRequest.getValue());
            toolProcess.setTimeCreated(currentTime);
            toolProcess.setTimeModified(currentTime);
            toolProcess.setIdUserCreated(csvcUser.getIdUser());
            toolProcess.setIdUserModified(csvcUser.getIdUser());
            toolProcess.setQuantity(toolProcessRequest.getQuantityIncrease());
            toolProcessList.add(toolProcess);
        }
        return toolProcessList;
    }

    private List<ToolProcess> constructionToolProcessDecrease(List<ToolDetailDecreaseRequest> toolProcessValue, Process process) {
        List<ToolProcess> toolProcessList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (ToolDetailDecreaseRequest toolProcessRequest: toolProcessValue){
            ToolProcess toolProcess = new ToolProcess();
            toolProcess.setIdTool(toolProcessRequest.getIdTool());
            toolProcess.setIdProcess(process.getIdProcess());
            toolProcess.setIdTypeProcess(process.getIdTypeProcess());
            toolProcess.setStatus(Constants.STATUS_TOOL_PROCESS_ACTIVE);
            toolProcess.setValue(toolProcessRequest.getValue());
            toolProcess.setTimeCreated(currentTime);
            toolProcess.setTimeModified(currentTime);
            toolProcess.setIdUserCreated(csvcUser.getIdUser());
            toolProcess.setIdUserModified(csvcUser.getIdUser());
            toolProcess.setQuantity(toolProcessRequest.getQuantityDecrease());
            toolProcessList.add(toolProcess);
        }
        return toolProcessList;
    }


    private List<ToolProcess> constructionToolProcessInventory(List<ToolDetailInventoryRequest> toolProcessValue, Process process) {
        List<ToolProcess> toolProcessList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (ToolDetailInventoryRequest toolProcessRequest: toolProcessValue){
            ToolProcess toolProcess = new ToolProcess();
            toolProcess.setIdTool(toolProcessRequest.getIdTool());
            toolProcess.setIdProcess(process.getIdProcess());
            toolProcess.setIdTypeProcess(process.getIdTypeProcess());
            toolProcess.setStatus(Constants.STATUS_TOOL_PROCESS_ACTIVE);
            toolProcess.setValue(toolProcessRequest.getValue());
            toolProcess.setTimeCreated(currentTime);
            toolProcess.setTimeModified(currentTime);
            toolProcess.setIdUserCreated(csvcUser.getIdUser());
            toolProcess.setIdUserModified(csvcUser.getIdUser());
            toolProcess.setQuantity(toolProcessRequest.getQuantityInventory());
            toolProcessList.add(toolProcess);
        }
        return toolProcessList;
    }

    private List<ToolProcess> constructionToolProcessUpdateInventory(List<ToolProcess> toolProcessesOriginal, Process process) {
        List<ToolProcess> toolProcessList = new ArrayList<>();
        String currentTime = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (ToolProcess toolProcessOriginal: toolProcessesOriginal){
            ToolProcess toolProcess = new ToolProcess();
            toolProcess.setIdTool(toolProcessOriginal.getIdTool());
            toolProcess.setIdProcess(process.getIdProcess());
            toolProcess.setIdTypeProcess(process.getIdTypeProcess());
            toolProcess.setStatus(Constants.STATUS_TOOL_PROCESS_ACTIVE);
            toolProcess.setValue(toolProcessOriginal.getValue());
            toolProcess.setTimeCreated(currentTime);
            toolProcess.setTimeModified(currentTime);
            toolProcess.setIdUserCreated(csvcUser.getIdUser());
            toolProcess.setIdUserModified(csvcUser.getIdUser());
            toolProcess.setQuantity(toolProcessOriginal.getQuantity());
            toolProcessList.add(toolProcess);
        }
        return toolProcessList;
    }


    private void createTaskSendMailIncrease(List<UserRoleDto> userRoleDtos, Document document, Process process) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (UserRoleDto stakeHolder : userRoleDtos){
            TaskSendMail taskSendMail = new TaskSendMail();
            taskSendMail.setCodeTaskSendMail(String.valueOf(UUID.randomUUID()));
            taskSendMail.setAddressFrom(PropertiesUtil.getEmailProperty("mail.user"));
            taskSendMail.setAddressTo(stakeHolder.getUserName());
            taskSendMail.setAddressCc(csvcUser.getUsername());
            taskSendMail.setSubject(EmailUtil.SUBJECTS_PROCESS[0]);
            taskSendMail.setStatus(Constants.STATUS_NOT_YET_TASK_SEND_MAIL);
            taskSendMail.setContent(EmailUtil.CONTENT_DOCUMENT
                            .replace(EmailUtil.KEY_TYPE_PROCESS,process.getName())
                            .replace(EmailUtil.KEY_CODE_DOCUMENT,document.getCode())
                            .replace(EmailUtil.KEY_FULL_NAME,csvcUser.getUsername())
                            .replace(EmailUtil.KEY_DESCRIPTION,document.getDescription())
                    .replace(EmailUtil.KEYWORD_REPLACE,EmailUtil.CONTENT_DOMAIN)
                    .replace(EmailUtil.KEYWORD_CODE_TASK_SEND_MAIL,taskSendMail.getCodeTaskSendMail()));
            taskSendMail.setRetry(Constants.INIT_RETRY);
            taskSendMail.setTimeCreated(timeCurrent);
            taskSendMail.setTimeModified(timeCurrent);
            taskSendMailService.saveTaskSendMail(taskSendMail);
        }
    }

    private void createTaskSendMailChange(List<UserRoleDto> userRoleDtos, Document document, Process process) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (UserRoleDto stakeHolder : userRoleDtos){
            TaskSendMail taskSendMail = new TaskSendMail();
            taskSendMail.setCodeTaskSendMail(String.valueOf(UUID.randomUUID()));
            taskSendMail.setAddressFrom(PropertiesUtil.getEmailProperty("mail.user"));
            taskSendMail.setAddressTo(stakeHolder.getUserName());
            taskSendMail.setAddressCc(csvcUser.getUsername());
            taskSendMail.setSubject(EmailUtil.SUBJECTS_PROCESS[4]);
            taskSendMail.setStatus(Constants.STATUS_NOT_YET_TASK_SEND_MAIL);
            taskSendMail.setContent(EmailUtil.CONTENT_DOCUMENT
                    .replace(EmailUtil.KEY_TYPE_PROCESS,process.getName())
                    .replace(EmailUtil.KEY_CODE_DOCUMENT,document.getCode())
                    .replace(EmailUtil.KEY_FULL_NAME,csvcUser.getUsername())
                    .replace(EmailUtil.KEY_DESCRIPTION,document.getDescription())
                    .replace(EmailUtil.KEYWORD_REPLACE,EmailUtil.CONTENT_DOMAIN)
                    .replace(EmailUtil.KEYWORD_CODE_TASK_SEND_MAIL,taskSendMail.getCodeTaskSendMail()));
            taskSendMail.setRetry(Constants.INIT_RETRY);
            taskSendMail.setTimeCreated(timeCurrent);
            taskSendMail.setTimeModified(timeCurrent);
            taskSendMailService.saveTaskSendMail(taskSendMail);
        }
    }

    private void createTaskSendMailRevaluation(List<UserRoleDto> userRoleDtos, Document document, Process process) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (UserRoleDto stakeHolder : userRoleDtos){
            TaskSendMail taskSendMail = new TaskSendMail();
            taskSendMail.setCodeTaskSendMail(String.valueOf(UUID.randomUUID()));
            taskSendMail.setAddressFrom(PropertiesUtil.getEmailProperty("mail.user"));
            taskSendMail.setAddressTo(stakeHolder.getUserName());
            taskSendMail.setAddressCc(csvcUser.getUsername());
            taskSendMail.setSubject(EmailUtil.SUBJECTS_PROCESS[3]);
            taskSendMail.setStatus(Constants.STATUS_NOT_YET_TASK_SEND_MAIL);
            taskSendMail.setContent(EmailUtil.CONTENT_DOCUMENT
                    .replace(EmailUtil.KEY_TYPE_PROCESS,process.getName())
                    .replace(EmailUtil.KEY_CODE_DOCUMENT,document.getCode())
                    .replace(EmailUtil.KEY_FULL_NAME,csvcUser.getUsername())
                    .replace(EmailUtil.KEY_DESCRIPTION,document.getDescription())
                    .replace(EmailUtil.KEYWORD_REPLACE,EmailUtil.CONTENT_DOMAIN)
                    .replace(EmailUtil.KEYWORD_CODE_TASK_SEND_MAIL,taskSendMail.getCodeTaskSendMail()));
            taskSendMail.setRetry(Constants.INIT_RETRY);
            taskSendMail.setTimeCreated(timeCurrent);
            taskSendMail.setTimeModified(timeCurrent);
            taskSendMailService.saveTaskSendMail(taskSendMail);
        }
    }

    @Override
    public void createDocumentInventoryAsset(CreateInventoryAssetRequest request) throws ValidateFiledException {
        List<Integer> idsAsset = new ArrayList<>();
        request.getAssetDetail().forEach(x->idsAsset.add(x.getIdAsset()));
        validateAssetProcessInventory(idsAsset);
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        documentService.saveDocument(constructionDocumentInventory(request.getDocument(), process));
        assetProcessService.saveListAssetProcess(constructionAssetProcessDocumentInventory(request, process));
        updateInformationProcessCurrentAsset(idsAsset, process);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(
                        Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED)
                );
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolderDocumentInventory(processRequest));
    }

    private void createUpdateInventoryAsset(Process processOriginal) throws ValidateFiledException, JsonProcessingException {
        List<AssetProcessDto> assetProcessDtos = assetProcessService.findAllAssetProcessByIdProcess(processOriginal.getIdProcess());
        Document documentOriginal = documentService.findDocumentByIdProcess(processOriginal.getIdProcess());
        List<Integer> idsAsset = new ArrayList<>();
        assetProcessDtos.forEach(x->idsAsset.add(x.getIdAsset()));
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY);
        Process process = processRepository.save(constructionDuplicationProcess(typeProcess, processOriginal));
        Document document = documentService.saveDocument(constructionUpdateInventory(documentOriginal, process));
        assetProcessService.saveListAssetProcess(constructionAssetProcessUpdateInventory(assetProcessDtos, process));
        updateInformationProcessCurrentAsset(idsAsset, process);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(
                        Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED)
        );
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        List<String> usersName = new ArrayList<>();
        List<CreateCouncilInventoryRequest> councilInventory = transferToCouncilInventory(documentOriginal.getDescription());
        councilInventory.forEach(x->usersName.add(x.getUserName()));
        List<FindAllUserDto> usersDto = csvcUserService.findIdsUserByUsersName(usersName);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolderUpdateInventory(processRequest, councilInventory, usersDto));
        createTaskSendMailInventory(usersDto, document, process);
    }

    private List<CreateCouncilInventoryRequest> transferToCouncilInventory(String descriptionOriginal) throws JsonProcessingException {
        HashMap<String, Object> content = (new ObjectMapper()).readValue(descriptionOriginal, new TypeReference<>() {});
        List<HashMap<String, Object>> councilContent = (List<HashMap<String, Object>>) content.get("councilInventory");
        List<CreateCouncilInventoryRequest> councilInventoryRequestList = new ArrayList<>();
        for (HashMap<String, Object> council : councilContent){
            CreateCouncilInventoryRequest councilInventoryRequest = new CreateCouncilInventoryRequest();
            councilInventoryRequest.setUserName(ValueUtil.getStringByObject(council.get("userName")));
            councilInventoryRequest.setCodeUser(ValueUtil.getStringByObject(council.get("codeUser")));
            councilInventoryRequest.setPosition(ValueUtil.getStringByObject(council.get("position")));
            councilInventoryRequest.setPositionInstance(ValueUtil.getStringByObject(council.get("positionInstance")));
            councilInventoryRequest.setLevel(ValueUtil.getIntegerByObject(council.get("level")));
            councilInventoryRequest.setIdDepartment(ValueUtil.getIntegerByObject(council.get("idDepartment")));
            councilInventoryRequestList.add(councilInventoryRequest);
        }
        return councilInventoryRequestList;
    }

    @Override
    public void createDecreaseAsset(CreateDecreaseAssetRequest request) throws ValidateFiledException {
        List<Integer> idsAsset = new ArrayList<>();
        request.getAssetDetail().forEach(x-> idsAsset.add(x.getIdAsset()));
        validateAssetProcessDecrease(idsAsset);
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(contructionDocumentDecrease(request.getDocument(), process));
        assetProcessService.saveListAssetProcess(contructionAssetProcessDecrease(request, process));
        updateInformationProcessCurrentAsset(idsAsset, process);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(
                        Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED)
        );
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        List<String> usersName = new ArrayList<>();
        request.getCouncilDecrease().forEach(x->usersName.add(x.getUserName()));
        List<FindAllUserDto> usersDto = csvcUserService.findIdsUserByUsersName(usersName);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolderDecrease(processRequest, request.getCouncilDecrease(),usersDto));
        createTaskSendMailDecrease(usersDto, document, process);
    }

    @Transactional
    @Override
    public void createChangeAsset(CreateChangeAssetRequest request) throws ValidateFiledException {
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(contructionDocumentChange(request.getDocument(), process));
        List<Integer> idsAssetChildren = new ArrayList<>();
        List<Asset> assetChildren= assetRepository.findAllAssetChildrenToChangeByParentId(request.getAssetDetail().getIdAsset());
        if(!assetChildren.isEmpty()){
            assetChildren.forEach(x->idsAssetChildren.add(x.getIdAsset()));
            validateAssetProcessChange(idsAssetChildren);
            assetProcessService.saveListAssetProcess(contructionAssetProcessLotChange(request, process,idsAssetChildren));
            updateInformationProcessCurrentAsset(idsAssetChildren, process);
            updateInformationProcessCurrentAsset(List.of(request.getAssetDetail().getIdAsset()), process);
        }
        else {
            validateAssetProcessChange(List.of(request.getAssetDetail().getIdAsset()));
            assetProcessService.saveListAssetProcess(contructionAssetProcessChange(request, process));
            updateInformationProcessCurrentAsset(List.of(request.getAssetDetail().getIdAsset()), process);
        }
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(
                        Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED)
        );
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        Integer idDepartment = process.getIdDepartment();
        List<UserRoleDto> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(),
                idDepartment);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, userRoles));
        createTaskSendMailChange(userRoles, document, process);
    }

    @Override
    public void createRevaluationAsset(CreateRevaluationAssetRequest request) throws ValidateFiledException{
        validateAssetProcessChange(List.of(request.getAssetDetail().getIdAsset()));
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(request.getTypeProcess());
        Process process = processRepository.save(constructionProcess(typeProcess));
        Document document = documentService.saveDocument(contructionDocumentRevaluation(request.getDocument(), process));
        assetProcessService.saveListAssetProcess(contructionAssetProcessRevaluation(request, process));
        updateInformationProcessCurrentAsset(List.of(request.getAssetDetail().getIdAsset()), process);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(Constants.CODE_TYPE_STATE_INIT,
                Constants.CODE_TYPE_STATE_TEST_APPROVED,
                Constants.CODE_TYPE_STATE_COMPLETED));
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        Integer idDepartment = process.getIdDepartment();
        List<UserRoleDto> userRoles = userRoleService.findUserRoleByNameRoleAndIdDepartment(RolePattern.ManagerDepartment.name(),
                idDepartment);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolder(processRequest, userRoles));
        createTaskSendMailRevaluation(userRoles, document, process);
    }

    private void validateAssetProcessDecrease(List<Integer> idsAsset) throws ValidateFiledException {
        Integer countAsset = assetService.countAssetByIdsAssetAndNotIncreasedOrDecreasedOrPending(idsAsset);
        if (countAsset != null && countAsset > 0) {
            throw new ValidateFiledException("Validate asset process to decreased!");
        }
    }

    private void validateAssetProcessChange(List<Integer> idsAsset) throws ValidateFiledException {
        Integer countAsset = assetService.countAssetByIdsAssetAndNotIncreasedOrDecreasedOrPending(idsAsset);
        if (countAsset != null && countAsset > 0) {
            throw new ValidateFiledException("Validate asset process to decreased!");
        }
    }


    private List<AssetProcess> constructionAssetProcessDocumentInventory(CreateInventoryAssetRequest request, Process process) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (AssetDetailInventoryRequest inventoryRequest : request.getAssetDetail()){
            AssetProcess assetProcess = new AssetProcess();
            assetProcess.setIdAsset(inventoryRequest.getIdAsset());
            assetProcess.setIdProcess(process.getIdProcess());
            assetProcess.setIdTypeProcess(process.getIdTypeProcess());
            assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
            assetProcess.setValue(inventoryRequest.getValue());
            assetProcess.setTimeCreated(timeCurrent);
            assetProcess.setTimeModified(timeCurrent);
            assetProcess.setIdUserCreated(csvcUser.getIdUser());
            assetProcess.setIdUserModified(csvcUser.getIdUser());
            assetProcessList.add(assetProcess);
        }
        return assetProcessList;
    }

    private List<AssetProcess> constructionAssetProcessUpdateInventory(List<AssetProcessDto> assetProcessDtos, Process process) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (AssetProcessDto assetProcessDto : assetProcessDtos){
            AssetProcess assetProcess = new AssetProcess();
            assetProcess.setIdAsset(assetProcessDto.getIdAsset());
            assetProcess.setIdProcess(process.getIdProcess());
            assetProcess.setIdTypeProcess(process.getIdTypeProcess());
            assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
            assetProcess.setValue(assetProcessDto.getValue());
            assetProcess.setTimeCreated(timeCurrent);
            assetProcess.setTimeModified(timeCurrent);
            assetProcess.setIdUserCreated(process.getIdUserCreated());
            assetProcess.setIdUserModified(process.getIdUserModified());
            assetProcessList.add(assetProcess);
        }
        return assetProcessList;
    }


//    private List<AssetProcess> contructionAssetProcessInventory(CreateInventoryAssetRequest request, Process process) {
//        List<AssetProcess> assetProcessList = new ArrayList<>();
//        String timeCurrent = String.valueOf(new Date().getTime());
//        for (AssetDetailInventoryRequest inventoryRequest : request.getAssetDetail()){
//            AssetProcess assetProcess = new AssetProcess();
//            assetProcess.setIdAsset(inventoryRequest.getIdAsset());
//            assetProcess.setIdProcess(process.getIdProcess());
//            assetProcess.setIdTypeProcess(process.getIdTypeProcess());
//            assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
//            assetProcess.setValue(inventoryRequest.getValue());
//            assetProcess.setTimeCreated(timeCurrent);
//            assetProcess.setTimeModified(timeCurrent);
//            assetProcessList.add(assetProcess);
//        }
//        return assetProcessList;
//    }

    private List<AssetProcess> contructionAssetProcessDecrease(CreateDecreaseAssetRequest request, Process process) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (AssetDetailDecreaseRequest inventoryRequest : request.getAssetDetail()){
            AssetProcess assetProcess = new AssetProcess();
            assetProcess.setIdAsset(inventoryRequest.getIdAsset());
            assetProcess.setIdProcess(process.getIdProcess());
            assetProcess.setIdTypeProcess(process.getIdTypeProcess());
            assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
            assetProcess.setValue(inventoryRequest.getValue());
            assetProcess.setTimeCreated(timeCurrent);
            assetProcess.setTimeModified(timeCurrent);
            assetProcess.setIdUserCreated(csvcUser.getIdUser());
            assetProcess.setIdUserModified(csvcUser.getIdUser());
            assetProcessList.add(assetProcess);
        }
        return assetProcessList;
    }

    private List<AssetProcess> contructionAssetProcessChange(CreateChangeAssetRequest request, Process process) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AssetProcess assetProcess = new AssetProcess();
        assetProcess.setIdAsset(request.getAssetDetail().getIdAsset());
        assetProcess.setIdProcess(process.getIdProcess());
        assetProcess.setIdTypeProcess(process.getIdTypeProcess());
        assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
        assetProcess.setValue(request.getAssetDetail().getValue());
        assetProcess.setTimeCreated(timeCurrent);
        assetProcess.setTimeModified(timeCurrent);
        assetProcess.setIdUserCreated(csvcUser.getIdUser());
        assetProcess.setIdUserModified(csvcUser.getIdUser());
        assetProcessList.add(assetProcess);
        return assetProcessList;
    }
    private List<AssetProcess> contructionAssetProcessLotChange(CreateChangeAssetRequest request, Process process, List<Integer> idsAssetChildren) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Integer idAssetChildren : idsAssetChildren) {
        AssetProcess assetProcess = new AssetProcess();
        assetProcess.setIdAsset(idAssetChildren);
        assetProcess.setIdProcess(process.getIdProcess());
        assetProcess.setIdTypeProcess(process.getIdTypeProcess());
        assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
        assetProcess.setValue(request.getAssetDetail().getValue());
        assetProcess.setTimeCreated(timeCurrent);
        assetProcess.setTimeModified(timeCurrent);
        assetProcess.setIdUserCreated(csvcUser.getIdUser());
        assetProcess.setIdUserModified(csvcUser.getIdUser());
        assetProcessList.add(assetProcess);
    }
        return assetProcessList;
    }

    private List<AssetProcess> contructionAssetProcessRevaluation(CreateRevaluationAssetRequest request, Process process) {
        List<AssetProcess> assetProcessList = new ArrayList<>();
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        AssetProcess assetProcess = new AssetProcess();
        assetProcess.setIdAsset(request.getAssetDetail().getIdAsset());
        assetProcess.setIdProcess(process.getIdProcess());
        assetProcess.setIdTypeProcess(process.getIdTypeProcess());
        assetProcess.setStatus(Constants.STATUS_ASSET_PROCESS_ACTIVE);
        assetProcess.setValue(request.getAssetDetail().getValue());
        assetProcess.setTimeCreated(timeCurrent);
        assetProcess.setTimeModified(timeCurrent);
        assetProcess.setIdUserCreated(csvcUser.getIdUser());
        assetProcess.setIdUserModified(csvcUser.getIdUser());
        assetProcessList.add(assetProcess);
        return assetProcessList;
    }

    private void validateAssetProcessInventory(List<Integer> idsAsset) throws ValidateFiledException {
        Integer countAsset = assetService.countAssetByIdsAssetAndNotIncreasedOrDecreasedOrPending(idsAsset);
        if (countAsset != null && countAsset > 0) {
            throw new ValidateFiledException("Validate asset process to inventory!");
        }
    }

    private void createTaskSendMailInventory(List<FindAllUserDto> userRoleDtos, Document document, Process process) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (FindAllUserDto stakeHolder : userRoleDtos){
            TaskSendMail taskSendMail = new TaskSendMail();
            taskSendMail.setCodeTaskSendMail(String.valueOf(UUID.randomUUID()));
            taskSendMail.setAddressFrom(PropertiesUtil.getEmailProperty("mail.user"));
            taskSendMail.setAddressTo(stakeHolder.getUserName());
            taskSendMail.setAddressCc(csvcUser.getUsername());
            taskSendMail.setSubject(EmailUtil.SUBJECTS_PROCESS[5]);
            taskSendMail.setStatus(Constants.STATUS_NOT_YET_TASK_SEND_MAIL);
            taskSendMail.setContent(EmailUtil.CONTENT_DOCUMENT
                    .replace(EmailUtil.KEY_TYPE_PROCESS,process.getName())
                    .replace(EmailUtil.KEY_CODE_DOCUMENT,document.getCode())
                    .replace(EmailUtil.KEY_FULL_NAME,csvcUser.getUsername())
                    .replace(EmailUtil.KEY_DESCRIPTION,document.getDescription())
                    .replace(EmailUtil.KEYWORD_REPLACE,EmailUtil.CONTENT_DOMAIN)
                    .replace(EmailUtil.KEYWORD_CODE_TASK_SEND_MAIL,taskSendMail.getCodeTaskSendMail()));
            taskSendMail.setRetry(Constants.INIT_RETRY);
            taskSendMail.setTimeCreated(timeCurrent);
            taskSendMail.setTimeModified(timeCurrent);
            taskSendMailService.saveTaskSendMail(taskSendMail);
        }
    }

    private void createTaskSendMailDecrease(List<FindAllUserDto> userRoleDtos, Document document, Process process) {
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (FindAllUserDto stakeHolder : userRoleDtos){
            TaskSendMail taskSendMail = new TaskSendMail();
            taskSendMail.setCodeTaskSendMail(String.valueOf(UUID.randomUUID()));
            taskSendMail.setAddressFrom(PropertiesUtil.getEmailProperty("mail.user"));
            taskSendMail.setAddressTo(stakeHolder.getUserName());
            taskSendMail.setAddressCc(csvcUser.getUsername());
            taskSendMail.setSubject(EmailUtil.SUBJECTS_PROCESS[1]);
            taskSendMail.setStatus(Constants.STATUS_NOT_YET_TASK_SEND_MAIL);
            taskSendMail.setContent(EmailUtil.CONTENT_DOCUMENT
                    .replace(EmailUtil.KEY_TYPE_PROCESS,process.getName())
                    .replace(EmailUtil.KEY_CODE_DOCUMENT,document.getCode())
                    .replace(EmailUtil.KEY_FULL_NAME,csvcUser.getUsername())
                    .replace(EmailUtil.KEY_DESCRIPTION,document.getDescription())
                    .replace(EmailUtil.KEYWORD_REPLACE,EmailUtil.CONTENT_DOMAIN)
                    .replace(EmailUtil.KEYWORD_CODE_TASK_SEND_MAIL,taskSendMail.getCodeTaskSendMail()));
            taskSendMail.setRetry(Constants.INIT_RETRY);
            taskSendMail.setTimeCreated(timeCurrent);
            taskSendMail.setTimeModified(timeCurrent);
            taskSendMailService.saveTaskSendMail(taskSendMail);
        }
    }

    private List<RequestStakeHolder> constructionRequestStakeHolderUpdateInventory(Request processRequest,
                                                                                   List<CreateCouncilInventoryRequest> councilInventory,
                                                                                   List<FindAllUserDto> usersDto) {
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (CreateCouncilInventoryRequest council : councilInventory){
            RequestStakeHolder stakeHolder = new RequestStakeHolder();
            stakeHolder.setIdRequest(processRequest.getIdRequest());
            stakeHolder.setIdUser(usersDto.stream().filter(x->x.getUserName().equals(council.getUserName())).findFirst().get().getIdUser());
            stakeHolder.setStatus(Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
            stakeHolder.setTimeCreated(timeCurrent);
            stakeHolder.setTimeModified(timeCurrent);
            stakeHolder.setIdDepartment(council.getIdDepartment());
            stakeHolder.setPosition(council.getPosition());
            stakeHolder.setPositionInstance(council.getPositionInstance());
            stakeHolder.setLevel(council.getLevel());
            stakeHolders.add(stakeHolder);
        }
        return stakeHolders;
    }


    private List<RequestStakeHolder> constructionRequestStakeHolderDocumentInventory(Request processRequest) {
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        RequestStakeHolder stakeHolder = new RequestStakeHolder();
        stakeHolder.setIdRequest(processRequest.getIdRequest());
        stakeHolder.setStatus(Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
        stakeHolder.setTimeCreated(timeCurrent);
        stakeHolder.setTimeModified(timeCurrent);
        stakeHolders.add(stakeHolder);
        return stakeHolders;
    }

    private List<RequestStakeHolder> constructionRequestStakeHolderDecrease(Request processRequest,
                                                                             List<CreateCouncilDecreaseRequest> councilDecrease,
                                                                             List<FindAllUserDto> usersDto) {
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (CreateCouncilDecreaseRequest council : councilDecrease){
            RequestStakeHolder stakeHolder = new RequestStakeHolder();
            stakeHolder.setIdRequest(processRequest.getIdRequest());
            stakeHolder.setIdUser(usersDto.stream().filter(x->x.getUserName().equals(council.getUserName())).findFirst().get().getIdUser());
            stakeHolder.setStatus(Constants.STATUS_REQUEST_STAKE_HOLDER_PENDING);
            stakeHolder.setTimeCreated(timeCurrent);
            stakeHolder.setTimeModified(timeCurrent);
            stakeHolder.setIdDepartment(council.getIdDepartment());
            stakeHolder.setPosition(council.getPosition());
            stakeHolder.setPositionInstance(council.getPositionInstance());
            stakeHolder.setLevel(council.getLevel());
            stakeHolders.add(stakeHolder);
        }
        return stakeHolders;
    }


    private void validateCreateNewDocumentIncrease(CreateDocumentRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeIncrease())
                || StringUtils.isBlank(request.getTimeDocument())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }

    private void validateCreateNewDocumentInventory(CreateDocumentInventoryAssetRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeCreatedDocument())
                || StringUtils.isBlank(request.getTimeInventory())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }

    private void validateCreateNewDocumentInventory(CreateDocumentInventoryToolRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeCreatedDocument())
                || StringUtils.isBlank(request.getTimeInventory())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }


    private void validateCreateNewDocumentDecrease(CreateDocumentDecreaseAssetRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeDocument())
                || StringUtils.isBlank(request.getTimeDecrease())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }

    private void validateCreateNewDocumentDecreaseTool(CreateDocumentDecreaseAssetRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeDocument())
                || StringUtils.isBlank(request.getTimeDecrease())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }


    private void validateCreateNewDocumentChange(CreateDocumentChangeAssetRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeDocument())
                || StringUtils.isBlank(request.getTimeChange())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }

    private void validateCreateNewDocumentRevaluation(CreateDocumentRevaluationAssetRequest request) throws ValidateFiledException {
        if (StringUtils.isBlank(request.getCodeDocument()) || StringUtils.isBlank(request.getTimeDocument())
                || StringUtils.isBlank(request.getTimeRevaluation())){
            throw new ValidateFiledException("Validate data create document!");
        }
    }

    private Document constructionDocumentIncrease(CreateDocumentRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocumentIncrease(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeIncrease());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(null);
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }

    private Document constructionDocumentDecrease(CreateDocumentDecreaseAssetRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocumentDecrease(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeDecrease());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(null);
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }

    private Document constructionDocumentInventory(CreateDocumentInventoryToolRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocumentInventory(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeInventory());
        document.setTimeDocument(request.getTimeInventory());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(request.getIdDepartment());
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }


    private Document constructionDocumentInventory(CreateDocumentInventoryAssetRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocumentInventory(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeInventory());
        document.setTimeDocument(request.getTimeCreatedDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(request.getIdDepartment());
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }

    private Document constructionUpdateInventory(Document documentOriginal, Process process){
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(ValueUtil.replaceGenerateCode(documentOriginal.getCode(),
                Constants.TYPE_GENERATE_DOCUMENT_INVENTORY, Constants.TYPE_GENERATE_UPDATE_INVENTORY));
        document.setIdProcess(process.getIdProcess());
        document.setDescription(documentOriginal.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(documentOriginal.getTimeIncrease());
        document.setTimeDocument(document.getTimeDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(documentOriginal.getIdDepartment());
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }

    private Document contructionDocumentDecrease(CreateDocumentDecreaseAssetRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocumentDecrease(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeDecrease());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(null);
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }

    private Document contructionDocumentChange(CreateDocumentChangeAssetRequest request, Process process) throws ValidateFiledException {
        validateCreateNewDocumentChange(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeChange());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(null);
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }

    private Document contructionDocumentRevaluation(CreateDocumentRevaluationAssetRequest request, Process process)
            throws ValidateFiledException {
        validateCreateNewDocumentRevaluation(request);
        Document document = new Document();
        String dateNow = String.valueOf(new Date().getTime());
        document.setCode(request.getCodeDocument());
        document.setIdProcess(process.getIdProcess());
        document.setDescription(request.getDescription());
        document.setTimeCreated(dateNow);
        document.setTimeModified(dateNow);
        document.setTimeIncrease(request.getTimeRevaluation());
        document.setTimeDocument(request.getTimeDocument());
        document.setIdDepartmentOriginal(process.getIdDepartment());
        document.setIdDepartment(null);
        document.setStatus(Constants.STATUS_DOCUMENT_CAN_CHANGE_OR_UPDATE);
        document.setIdUserCreated(process.getIdUserCreated());
        document.setIdUserModified(process.getIdUserModified());
        return document;
    }


    @Override
    public Process  findProcessByIdProcess(Integer idProcess) {
        Optional<Process> process =  processRepository.findProcessByIdProcess(idProcess);
        if (process.isEmpty()){
            throw new NotFoundException("Don't exits process by id process!");
        }
        return process.get();
    }

    @Override
    public Process updateProcessByIdProcessAndStatus(Integer idProcess, Integer status) throws ValidateFiledException,
            JsonProcessingException, IllegalAccessException {
        Process process =  findProcessByIdProcess(idProcess);
        process.setStatus(status);
        processRepository.save(process);
        handleAssetByTypeProcess(process, status, typeProcessService.findTypeProcessByIdTypeProcess(process.getIdTypeProcess()));
        return process;
    }

    void handleAssetByTypeProcess(Process process, Integer status, TypeProcess typeProcess)
            throws ValidateFiledException, JsonProcessingException, IllegalAccessException {
        if (status.equals(Constants.STATUS_SUCCESS_PROCESS)) {
            handleApproved(process, status, typeProcess);
        } else {
            handleNotApproved(process, status, typeProcess);
        }
    }

    private void handleApproved(Process process, Integer status, TypeProcess typeProcess)
            throws ValidateFiledException, JsonProcessingException, IllegalAccessException {
        switch (typeProcess.getCode()) {
            case Constants.CODE_TYPE_PROCESS_INCREASE,
                    Constants.CODE_TYPE_PROCESS_DECREASE -> {
                assetService.updateAssetStatusProcessCurrentAndIsIncreaseAndIsDecrease(process.getIdProcess(),
                        status, typeProcess.getCode());
                assetService.updateIncreaseOrDecreaseAssetLotByIdProcess(process.getIdProcess(), typeProcess.getCode());
            }
            case Constants.CODE_TYPE_PROCESS_CHANGE,
                    Constants.CODE_TYPE_PROCESS_REVALUATION ->{
                assetService.updateInformationAssetByProcess(process, status);
                assetService.updateAssetStatusProcessCurrentByIdProcessCurrent(process.getIdProcess(), status);
            }
            case Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY -> {
                assetService.updateAssetStatusProcessCurrentByIdProcessCurrent(process.getIdProcess(), status);
                createUpdateInventoryAsset(process);
            }
            case Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY-> {
                assetService.updateAssetStatusProcessCurrentByIdProcessCurrent(process.getIdProcess(), status);
            }
            case Constants.CODE_TYPE_PROCESS_INCREASE_TOOL,
                    Constants.CODE_TYPE_PROCESS_DECREASE_TOOL-> {
                toolService.updateToolStatusProcessCurrentAndIsIncreaseAndIsDecrease(process.getIdProcess(),
                        status, typeProcess.getCode());
                toolService.updateToolParentIsIncreaseAndIsDecrease(process.getIdProcess(), typeProcess.getCode());
            }
            case Constants.CODE_TYPE_PROCESS_DOCUMENT_INVENTORY_TOOL -> {
                toolService.updateToolStatusProcessCurrentByIdProcessCurrent(process.getIdProcess(), status);
                createUpdateInventoryTool(process);
            }
            case Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY_TOOL-> {
                toolService.updateToolStatusProcessCurrentByIdProcessCurrent(process.getIdProcess(), status);
            }
            default -> {
                return;
            }
        }
    }

    private void createUpdateInventoryTool(Process processOriginal) throws ValidateFiledException, JsonProcessingException {
        List<ToolProcess> toolProcesses = toolProcessService.findAllToolProcessByIdProcess(processOriginal.getIdProcess());
        Document documentOriginal = documentService.findDocumentByIdProcess(processOriginal.getIdProcess());
        List<Integer> idsTool = new ArrayList<>();
        toolProcesses.forEach(x->idsTool.add(x.getIdTool()));
        TypeProcess typeProcess = typeProcessService.findTypeProcessByCode(Constants.CODE_TYPE_PROCESS_UPDATE_INVENTORY_TOOL);
        Process process = processRepository.save(constructionDuplicationProcess(typeProcess, processOriginal));
        Document document = documentService.saveDocument(constructionUpdateInventory(documentOriginal, process));
        toolProcessService.saveAllToolProcess(constructionToolProcessUpdateInventory(toolProcesses, process));
        updateInformationProcessCurrentTool(idsTool, process);
        List<TypeState> typeStates = typeStateService.findAllTypeStateByCodes(
                Arrays.asList(
                        Constants.CODE_TYPE_STATE_INIT,
                        Constants.CODE_TYPE_STATE_TEST_APPROVED,
                        Constants.CODE_TYPE_STATE_COMPLETED)
        );
        List<State> states = stateService.saveAllState(constructionStateList(process, typeStates));
        transitionService.saveTransition(constructionTransition(process, states));
        Request processRequest = requestService.createNewRequestProcess(constructionRequest(process,
                states.stream().filter(x->x.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)).findFirst().get().getIdState()));
        List<String> usersName = new ArrayList<>();
        List<CreateCouncilInventoryRequest> councilInventory = transferToCouncilInventory(documentOriginal.getDescription());
        councilInventory.forEach(x->usersName.add(x.getUserName()));
        List<FindAllUserDto> usersDto = csvcUserService.findIdsUserByUsersName(usersName);
        requestDataService.createNewRequestData(constructionRequestData(processRequest));
        requestStakeHolderService.createNewRequestStakeHolder(constructionRequestStakeHolderUpdateInventory(processRequest, councilInventory, usersDto));
        createTaskSendMailInventory(usersDto, document, process);
    }

    private void handleNotApproved(Process process, Integer status, TypeProcess typeProcess) {
        switch (typeProcess.getCode()) {
            case Constants.CODE_TYPE_PROCESS_INCREASE_TOOL,
                    Constants.CODE_TYPE_PROCESS_DECREASE_TOOL-> {
                toolService.updateToolStatusProcessCurrentByIdProcessCurrentWhenNotApproved(process.getIdProcess(),
                        status, typeProcess.getCode());
            }
            default -> {
                assetService.updateAssetStatusProcessCurrentByIdProcessCurrent(process.getIdProcess(), status);
            }
        }
    }

    @Override
    public Page<FindAllProcessBeAssignedResponse> findAllProcessBeAssignedResponse(FindAllProcessBeAssignedRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        return processRepository.findAllProcessBeAssigned(request, pageable);
    }

    @Override
    public Page<FindAllProcessBeAssignedResponse>
    findAllProcessBeAssignedDocumentInventoryResponse(FindAllProcessBeAssignedDocumentInventoryRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartment(csvcUser.getIdsDepartmentCurrent());
        return processRepository.findAllProcessBeAssignedDocumentInventory(request, pageable);
    }

    @Override
    public ProcessStatisticsIncreaseResponse getStatisticIncrease() {
        return processRepository.getStatisticsIncrease();
    }

    @Override
    public ProcessStatisticsUpdateInventoryResponse getStatisticUpdateInventory() {
        return processRepository.getStatisticsUpdateInventory();
    }

    @Override
    public ProcessStatisticsDocumentInventoryResponse getStatisticDocumentInventory() {
        return processRepository.getStatisticsDocumentInventory();
    }

    @Override
    public ProcessStatisticsDocumentBeInventoryResponse getStatisticDocumentBeInventory() {
        return processRepository.getStatisticsDocumentByInventory();
    }

    @Override
    public ProcessStatisticsDecreaseResponse getStatisticDecrease() {
        return processRepository.getStatisticsDecrease();
    }

    @Override
    public ProcessStatisticsChangeResponse getStatisticChange() {
        return processRepository.getStatisticsChange();
    }

    @Override
    public ProcessStatisticsRevaluationResponse getStatisticRevaluation() {
        return processRepository.getStatisticsRevaluation();
    }

    @Override
    public ProcessStatisticsToolIncreaseResponse getStatisticToolIncrease() {
        return processRepository.getStatisticsToolIncrease();
    }

    @Override
    public ProcessStatisticsToolDecreaseResponse getStatisticToolDecrease() {
        return processRepository.getStatisticsToolDecrease();
    }

    @Override
    public ProcessStatisticsToolDocumentInventoryResponse getStatisticToolDocumentInventory() {
        return processRepository.getStatisticsToolDocumentInventory();
    }

    @Override
    public ProcessStatisticsToolDocumentBeInventoryResponse getStatisticToolDocumentBeInventory() {
        return processRepository.getStatisticsToolDocumentByInventory();
    }

    @Override
    public ProcessStatisticsToolUpdateInventoryResponse getStatisticToolUpdateInventory() {
        return processRepository.getStatisticsToolUpdateInventory();
    }

    @Override
    public Page<FindAllDocumentToolBeAssignedDocumentInventoryResponse> findAllDocumentToolBeAssignedDocumentInventory(FindAllToolBeAssignedDocumentToolRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setIdsDepartment(csvcUser.getIdsDepartmentCurrent());
        return processRepository.findAllToolBeAssignedDocumentInventory(request, pageable);
    }

    /***
     *
     * HIỆN TẠI ĐANG SET QUYỀN MANAGER ĐƠN VỊ SẼ ĐƯỢC PHÊ DUYỆT -> CẦN CHỈNH SỬA LẠI QUYỀN ĐỂ LẤY RA AI JOIN VỀ QUYỀN
     *
     */

    private List<RequestStakeHolder> constructionRequestStakeHolder(Request processRequest, List<UserRoleDto> userRoles) {
        List<RequestStakeHolder> stakeHolders = new ArrayList<>();
        String timeCurrent = String.valueOf(new Date().getTime());
        for (UserRoleDto userRole : userRoles){
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
        request.setName(process.getName());
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
            if (state.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_TEST_APPROVED)){
                transition.setIdStateCurrent(state.getIdState());
            }
            if (state.getCodeTypeState().equals(Constants.CODE_TYPE_STATE_COMPLETED)){
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
            case Constants.CODE_TYPE_STATE_TEST_APPROVED:
                return Constants.STEP_TYPE_STATE_TEST_APPROVED;
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
            case Constants.CODE_TYPE_STATE_TEST_APPROVED:
                return Constants.STATUS_STATE_PENDING;
            case Constants.CODE_TYPE_STATE_COMPLETED:
                return Constants.STATUS_STATE_NOT_STARTED;
        }
        return 0;
    }


    private Process constructionProcess(TypeProcess typeProcess) {
        Process process = new Process();
        process.setIdTypeProcess(typeProcess.getIdTypeProcess());
        process.setName(typeProcess.getName());
        process.setStatus(Constants.STATUS_PENDING_PROCESS);
        String timeCurrent = String.valueOf(new Date().getTime());
        process.setTimeCreated(timeCurrent);
        process.setTimeModified(timeCurrent);
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer idDepartment = ((CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdDepartmentCurrent();
        process.setIdUserCreated(csvcUser.getIdUser());
        process.setIdUserModified(csvcUser.getIdUser());
        process.setIdDepartment(idDepartment);
        return process;
    }
    private Process constructionDuplicationProcess(TypeProcess typeProcess, Process processOriginal) {
        Process process = new Process();
        process.setIdTypeProcess(typeProcess.getIdTypeProcess());
        process.setName(typeProcess.getName());
        process.setStatus(Constants.STATUS_PENDING_PROCESS);
        String timeCurrent = String.valueOf(new Date().getTime());
        process.setTimeCreated(timeCurrent);
        process.setTimeModified(timeCurrent);
        process.setIdUserCreated(processOriginal.getIdUserCreated());
        process.setIdUserModified(process.getIdUserCreated());
        process.setIdDepartment(processOriginal.getIdDepartment());
        return process;
    }


}
