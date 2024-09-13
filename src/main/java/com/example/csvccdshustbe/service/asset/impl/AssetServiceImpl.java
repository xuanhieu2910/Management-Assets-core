package com.example.csvccdshustbe.service.asset.impl;

import com.example.csvccdshustbe.dto.asset.AssetBluePrintDto;
import com.example.csvccdshustbe.dto.asset.CommonAssetDto;
import com.example.csvccdshustbe.dto.asset.FindAllAssetDto;
import com.example.csvccdshustbe.dto.modules.AssetModulesDto;
import com.example.csvccdshustbe.dto.modules.BluePrintAssetModulesDto;
import com.example.csvccdshustbe.dto.original.BluePrintOriginalDto;
import com.example.csvccdshustbe.entity.*;
import com.example.csvccdshustbe.exception.ValidateFiledException;
import com.example.csvccdshustbe.factory.declare.DeclareFactory;
import com.example.csvccdshustbe.factory.module.ModuleFactory;
import com.example.csvccdshustbe.factory.original.OriginalFactory;
import com.example.csvccdshustbe.repository.asset.AssetRepository;
import com.example.csvccdshustbe.request.asset.FindAllAssetRequest;
import com.example.csvccdshustbe.response.asset.FindAllAssetResponse;
import com.example.csvccdshustbe.response.asset.FindDetailsAssetResponse;
import com.example.csvccdshustbe.service.asset.AssetService;
import com.example.csvccdshustbe.service.assetCategories.AssetCategoriesService;
import com.example.csvccdshustbe.service.assetOriginalOfFormation.AssetOriginalOfFormationService;
import com.example.csvccdshustbe.service.declare.DeclareServiceFactory;
import com.example.csvccdshustbe.service.department.DepartmentService;
import com.example.csvccdshustbe.service.documentAttack.DocumentAttackService;
import com.example.csvccdshustbe.service.location.LocationService;
import com.example.csvccdshustbe.service.modules.ModulesServiceFactory;
import com.example.csvccdshustbe.service.original.OriginalServiceFactory;
import com.example.csvccdshustbe.service.originalOfFormation.OriginalOfFormationService;
import com.example.csvccdshustbe.service.projects.ProjectsService;
import com.example.csvccdshustbe.service.units.UnitsService;
import com.example.csvccdshustbe.service.user.CsvcUserService;
import com.example.csvccdshustbe.utility.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.JSONObjectUtils;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AssetServiceImpl implements AssetService {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AssetRepository assetRepository;
    @Autowired
    DeclareServiceFactory declareServiceFactory;
    @Autowired
    OriginalServiceFactory originalServiceFactory;
    @Autowired
    ModulesServiceFactory modulesServiceFactory;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    LocationService locationService;
    @Autowired
    AssetCategoriesService assetCategoriesService;
    @Autowired
    UnitsService unitsService;
    @Autowired
    DocumentAttackService documentAttackService;
    @Autowired
    ProjectsService projectsService;
    @Autowired
    AssetOriginalOfFormationService assetOriginalOfFormationService;
    @Autowired
    CsvcUserService csvcUserService;
    @Autowired
    OriginalOfFormationService originalOfFormationService;





    @Transactional
    @Override
    public void createAsset(Map<String, Object> createAssetRequest) throws JsonProcessingException, ValidateFiledException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(createAssetRequest), Map.class);
        validateDataCreateAsset(dataCreateAssetRequest);
        storeNewAsset(dataCreateAssetRequest);
    }

    @Override
    public Page<FindAllAssetResponse> findAllAsset(FindAllAssetRequest request) {
        Pageable pageable = PageUtils.buildPage(request.getPage(), request.getSize());
        Page<FindAllAssetDto> findAllAssetDtos = assetRepository.findAllAssetDto(request, pageable);
        return new PageImpl<>(convertToFindAllAssetResponse(findAllAssetDtos.get().collect(Collectors.toList())),
                    pageable, findAllAssetDtos.getTotalElements());
    }

    @Override
    public FindDetailsAssetResponse findDetailsAssetByCodeAsset(String codeAsset) throws ValidateFiledException, IllegalAccessException {
        Optional<AssetBluePrintDto> assetBluePrintDto = assetRepository.findDetailAssetByCodeAsset(codeAsset);
        if (assetBluePrintDto.isEmpty()) {
            throw new NotFoundException("Don't exits asset by code!");
        }
        setParentAssetCategory(assetBluePrintDto.get());
        setOriginalOfFormation(assetBluePrintDto.get());
        setDataModulesDetail(assetBluePrintDto.get());
        setDataOriginalDetail(assetBluePrintDto.get());
        setDataDeclareDetail(assetBluePrintDto.get());
        return convertToFindDetailsAssetResponse(assetBluePrintDto.get());
    }

    @Transactional
    @Override
    public void updateAsset(HashMap<String, Object> updateAssetRequest) throws JsonProcessingException, ValidateFiledException,
            IllegalAccessException {
        Map<String, Object> dataCreateAssetRequest =
                objectMapper.readValue(JSONObjectUtils.toJSONString(updateAssetRequest), Map.class);
        validateDataUpdateAsset(dataCreateAssetRequest);
        updateDataAsset(dataCreateAssetRequest);
    }

    private void updateDataAsset(Map<String, Object> dataUpdateAssetRequest) throws ValidateFiledException, IllegalAccessException {
        Asset asset = updateCommonDataAsset(dataUpdateAssetRequest);
        updateModulesDataAsset(dataUpdateAssetRequest, asset);
        updateOriginalDataAsset(dataUpdateAssetRequest, asset);
        updateDeclareDataAsset(dataUpdateAssetRequest, asset);
    }

    private void updateDeclareDataAsset(Map<String, Object> dataUpdateAssetRequest, Asset asset) {
        log.info("Start update declare data asset by code asset " + asset.getCodeAsset());
        Map<String,Object> declareDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_DECLARE_ASSET);
    }

    private void updateOriginalDataAsset(Map<String, Object> dataUpdateAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Start update original data asset by code asset " + asset.getCodeAsset());
        Map<String,Object> originalDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_ORIGINAL_ASSET);
        BluePrintOriginalDto bluePrintOriginalDto = originalServiceFactory.findBluePrintAssetOriginalByIdAsset(asset.getIdAsset());
        deleteAssetOriginal(bluePrintOriginalDto, originalDataAsset);
        createNewAssetOriginal(bluePrintOriginalDto, originalDataAsset, asset);
        updateAssetOriginal(bluePrintOriginalDto, originalDataAsset);
    }

    private void updateAssetOriginal(BluePrintOriginalDto bluePrintOriginalDto, Map<String, Object> originalDataAsset) {
        if (!bluePrintOriginalDto.getTypeOriginal().equals(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)))){
            String typeOriginal = bluePrintOriginalDto.getTypeOriginal();
            Integer idInstance = bluePrintOriginalDto.getIdInstance();
            IOriginal iOriginal = OriginalServiceFactory.findIOriginalByTypeOriginalAndIdInstance(typeOriginal, idInstance);
        }
    }

    private void createNewAssetOriginal(BluePrintOriginalDto bluePrintOriginalDto, Map<String, Object> originalDataAssetRq, Asset asset) throws ValidateFiledException {
        if (!bluePrintOriginalDto.getTypeOriginal().equals(ValueUtil.getStringByObject(originalDataAssetRq.get(Constants.KEY_TYPE_ORIGINAL_ASSET)))){
            log.info("Storing original data asset");
            Map<String,Object> originalDataAsset = (Map<String, Object>) originalDataAssetRq.get(Constants.KEY_ORIGINAL_ASSET);
            originalDataAsset.put("idAsset", asset.getIdAsset());
            OriginalFactory originalFactory = (OriginalFactory) ProxyInitDataAssetUtil.
                    proxyInitOriginalDataAsset(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)));
            IOriginal iOriginal = originalFactory.createOriginal(originalDataAsset);
            originalServiceFactory.save(iOriginal, originalDataAsset);
            log.info("Finish store original factory " + originalFactory.getClass());
        }
    }

    private void deleteAssetOriginal(BluePrintOriginalDto bluePrintOriginalDto, Map<String, Object> originalDataAsset) throws ValidateFiledException {
        if (!bluePrintOriginalDto.getTypeOriginal().equals(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)))){
            originalServiceFactory.deleteAssetOriginal(bluePrintOriginalDto.getTypeOriginal(), bluePrintOriginalDto.getIdInstance(),
                    bluePrintOriginalDto.getIdOriginal());
        }
    }

    private void updateModulesDataAsset(Map<String, Object> dataUpdateAssetRequest, Asset asset) throws ValidateFiledException, IllegalAccessException {
        log.info("Start update modules data asset by code asset " + asset.getCodeAsset());
        List<BluePrintAssetModulesDto> bluePrintAssetModulesDtos = modulesServiceFactory.findBluePrintAssetModulesByIdAsset(asset.getIdAsset());
        List<HashMap<String,Object>> modulesDataAsset = (List<HashMap<String,Object>>) dataUpdateAssetRequest.get(Constants.KEY_MODULE);

        deleteAssetModule(bluePrintAssetModulesDtos, modulesDataAsset);
        createNewAssetModule(bluePrintAssetModulesDtos,modulesDataAsset,asset);
        updateAssetModule(bluePrintAssetModulesDtos, modulesDataAsset);
    }

    private void updateAssetModule(List<BluePrintAssetModulesDto> bluePrintAssetModulesDtos,
                                   List<HashMap<String, Object>> dataModule) throws ValidateFiledException, IllegalAccessException {
        for (HashMap<String,Object> dataAsset: dataModule) {
            String typeModules;
            Integer idInstance;
            for (BluePrintAssetModulesDto bluePrintAssetModulesDto : bluePrintAssetModulesDtos) {
                if (ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)).
                        equals(bluePrintAssetModulesDto.getTypeModules())) {
                    typeModules = bluePrintAssetModulesDto.getTypeModules();
                    idInstance = bluePrintAssetModulesDto.getIdModules();
                    IModules iModulesDetails = modulesServiceFactory.
                            findDataModulesByTypeModulesAndIdInstance(typeModules,idInstance);
                    ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                            proxyInitModuleDataAsset(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)));
                    IModules iModules = moduleFactory.updateModule(dataAsset,iModulesDetails);
                    modulesServiceFactory.update(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)), iModules);
                }
            }
        }

    }

    private void createNewAssetModule(List<BluePrintAssetModulesDto> assetModulesDtos ,
                                      List<HashMap<String,Object>> moduleDataAsset,
                                      Asset asset) throws ValidateFiledException {
        for (HashMap<String,Object> dataAsset: moduleDataAsset){
            boolean checkEqual = false;
            for (BluePrintAssetModulesDto bluePrintAssetModulesDto : assetModulesDtos){
                if (ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)).
                        equals(bluePrintAssetModulesDto.getTypeModules())) {
                    checkEqual = true;
                    break;
                }
            }
            if (!checkEqual){
                if (StringUtils.isNotBlank(ValueUtil.getStringByObject(dataAsset.get("codeUser")))) {
                    CsvcUser user = csvcUserService.findByCodeUser(ValueUtil.getStringByObject(dataAsset.get("codeUser")));
                    dataAsset.put("idUser", user.getIdUser());
                }
                dataAsset.put("idAsset", asset.getIdAsset());
                ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                        proxyInitModuleDataAsset(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)));
                IModules iModules = moduleFactory.createModule(dataAsset);
                modulesServiceFactory.save(iModules, dataAsset);
                log.info("Finish store module factory " + moduleFactory.getClass());
            }
        }
    }

    private void deleteAssetModule(List<BluePrintAssetModulesDto> bluePrintAssetModulesDtoList,
                                   List<HashMap<String,Object>> modulesDataAsset ) throws ValidateFiledException {
        for (BluePrintAssetModulesDto bluePrintAssetModulesDto : bluePrintAssetModulesDtoList){
            boolean checkEqual = false;
            for (HashMap<String,Object> dataAsset : modulesDataAsset) {
                if (bluePrintAssetModulesDto.getTypeModules().
                        equals(ValueUtil.getStringByObject(dataAsset.get(Constants.KEY_TYPE_MODULE)))) {
                    checkEqual = true;
                    break;
                }
            }
            if (!checkEqual) {
                modulesServiceFactory.deleteModulesByTypeModulesAndIdInstance(bluePrintAssetModulesDto.getTypeModules(),
                        bluePrintAssetModulesDto.getIdInstance(), bluePrintAssetModulesDto.getIdModules());
            }
        }
    }

    private Asset updateCommonDataAsset(Map<String, Object> dataUpdateAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) dataUpdateAssetRequest.get(Constants.KEY_COMMON);
        log.info("Start update common data asset by code asset = " + ValueUtil.getStringByObject(commonDataAsset.get("codeAsset")));
        Optional<Asset> asset = assetRepository.findAssetByCodeAsset(ValueUtil.getStringByObject(commonDataAsset.get("codeAsset")));
        if (asset.isEmpty()) {
            throw new NotFoundException("Don't exits asset by code!");
        }
        updateAttributeAsset(commonDataAsset, asset.get());
        log.info("Update finished common data asset by code asset = " + asset.get().getCodeAsset());
        return asset.get();
    }

    private void updateAttributeAsset(Map<String, Object> commonDataAsset, Asset asset) {
        asset.setName(ValueUtil.getStringByObject(commonDataAsset.get("name")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(commonDataAsset.get("idAssetCategory")));
        asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(commonDataAsset.get("idDocumentAttack")));
        if (!asset.getIdDepartment().equals(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment")))) {
            if (StringUtils.isNotBlank(ValueUtil.getStringByObject(commonDataAsset.get("codeDepartment")))) {
                asset.setCodeAsset(ValueUtil.getStringByObject(commonDataAsset.get("codeDepartment")) + "-" + UUID.randomUUID());
            } else {
                asset.setCodeAsset(String.valueOf(UUID.randomUUID()));
            }
            asset.setIdDepartment(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment")));
        }
        asset.setIdLocation(ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(commonDataAsset.get("idUnit")));
        asset.setIdProjects(ValueUtil.getIntegerByObject(commonDataAsset.get("idProjects")));
        asset.setPurpose(ValueUtil.getStringByObject(commonDataAsset.get("purpose")));
        asset.setNotes(ValueUtil.getStringByObject(commonDataAsset.get("notes")));
        asset.setDescription(ValueUtil.getStringByObject(commonDataAsset.get("description")));
        asset.setQuantity(ValueUtil.getIntegerByObject(commonDataAsset.get("quantity")));
        asset.setFileAttack(ValueUtil.getStringByObject(commonDataAsset.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        asset.setTimeModified(timeCurrent);
        asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartmentDefault")));
        asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(commonDataAsset.get("idLevelTypeAsset")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asset.setIdUserModified(csvcUser.getIdUser());
        assetRepository.save(asset);
    }

    private void validateDataUpdateAsset(Map<String, Object> dataCreateAssetRequest) {
    }

    private FindDetailsAssetResponse convertToFindDetailsAssetResponse(AssetBluePrintDto assetBluePrintDto) {
        FindDetailsAssetResponse response = new FindDetailsAssetResponse();
        response.setCodeParentAssetCategory(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getCodeParentAssetCategory());
        response.setIdParentAssetCategory(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getIdParentAssetCategory());
        response.setNameParentAssetCategory(assetBluePrintDto.getBluePrintParentAssetCategoryDto().getNameParentAssetCategory());
        response.setCommon(setCommonDetailsAssetResponse(assetBluePrintDto));
        response.setModules(assetBluePrintDto.getModules());
        response.setOriginal(assetBluePrintDto.getOriginal());
        response.setDeclare(assetBluePrintDto.getDeclare());
        return response;
    }


    private CommonAssetDto setCommonDetailsAssetResponse(AssetBluePrintDto assetBluePrintDto) {
        CommonAssetDto commonAssetDto = new CommonAssetDto();
        commonAssetDto.setIdAsset(assetBluePrintDto.getIdAsset());
        commonAssetDto.setName(assetBluePrintDto.getName());
        commonAssetDto.setCodeAsset(assetBluePrintDto.getCodeAsset());
        commonAssetDto.setAssetCategory(assetBluePrintDto.getAssetCategory());
        commonAssetDto.setDepartment(assetBluePrintDto.getDepartment());
        commonAssetDto.setDocumentAttack(assetBluePrintDto.getDocumentAttack());
        commonAssetDto.setLocation(assetBluePrintDto.getLocation());
        commonAssetDto.setUnits(assetBluePrintDto.getUnits());
        commonAssetDto.setProjects(assetBluePrintDto.getProjects());
        commonAssetDto.setPurpose(assetBluePrintDto.getPurpose());
        commonAssetDto.setNotes(assetBluePrintDto.getNotes());
        commonAssetDto.setDescription(assetBluePrintDto.getDescription());
        commonAssetDto.setQuantity(assetBluePrintDto.getQuantity());
        commonAssetDto.setFileAttack(assetBluePrintDto.getFileAttack());
        commonAssetDto.setDepartmentDefault(assetBluePrintDto.getDepartmentDefault());
        commonAssetDto.setLevelTypeAsset(assetBluePrintDto.getLevelTypeAsset());
        commonAssetDto.setOriginOfFormation(assetBluePrintDto.getOriginOfFormation());
        commonAssetDto.setIdInstance(assetBluePrintDto.getIdInstance());
        return commonAssetDto;
    }

    private void setDataDeclareDetail(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException, IllegalAccessException {
        String typeDeclare = assetBluePrintDto.getDeclare().getBluePrintDeclare().getTypeDeclare();
        Integer idInstance = assetBluePrintDto.getDeclare().getBluePrintDeclare().getIdInstance();
        Map<String, Object> dataDeclare = declareServiceFactory.findDataDetailByTypeDeclareAndIdInstance(typeDeclare, idInstance);
        assetBluePrintDto.getDeclare().setDataDetail(dataDeclare);
    }

    private void setDataOriginalDetail(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException, IllegalAccessException {
        String typeOriginal = assetBluePrintDto.getOriginal().getBluePrintOriginalDto().getTypeOriginal();
        Integer idInstance = assetBluePrintDto.getOriginal().getBluePrintOriginalDto().getIdInstance();
        Map<String,Object> dataOriginal = originalServiceFactory.findDataDetailByTypeOriginalAndIdInstance(typeOriginal, idInstance);
        assetBluePrintDto.getOriginal().setDataDetails(dataOriginal);
    }

    private void setDataModulesDetail(AssetBluePrintDto assetBluePrintDto) throws ValidateFiledException, IllegalAccessException {
        for (AssetModulesDto modulesDto : assetBluePrintDto.getModules()) {
            String typeModules = modulesDto.getBluePrintAssetModules().getTypeModules();
            Integer idInstance = modulesDto.getBluePrintAssetModules().getIdInstance();
            Map<String, Object> dataModules = modulesServiceFactory.findDataDetailByTypeModulesAndIdInstance(typeModules, idInstance);
            modulesDto.setDataDetail(dataModules);
        }
    }

    private void setOriginalOfFormation(AssetBluePrintDto assetBluePrintDto) {
        assetBluePrintDto.
                setOriginOfFormation(assetOriginalOfFormationService.
                        findOriginalOfFormationByIdAsset(assetBluePrintDto.getIdAsset()));
    }

    private void setParentAssetCategory(AssetBluePrintDto assetBluePrintDto){
        assetBluePrintDto.setBluePrintParentAssetCategoryDto(
                        assetCategoriesService.findBluePrintParentAssetCategoryDtoById(assetBluePrintDto.getIdInstance()));
    }

    private List<FindAllAssetResponse> convertToFindAllAssetResponse(List<FindAllAssetDto> collect) {
        List<FindAllAssetResponse> responses = new ArrayList<>();
        for (FindAllAssetDto dto : collect) {
            FindAllAssetResponse response = new FindAllAssetResponse();
            response.setCodeAsset(dto.getCodeAsset());
            response.setNameAsset(dto.getNameAsset());
            response.setNameAssetCategory(dto.getNameAssetCategory());
            response.setCodeAssetCategory(dto.getCodeAssetCategory());
            response.setCodeDepartment(dto.getCodeDepartment());
            response.setNameDepartment(dto.getNameDepartment());
            response.setTimeCreated(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeCreated(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            response.setTimeModified(DateUtil.formatToPattern(
                    DateUtil.formatDatePattern(dto.getTimeModified(),
                            DateUtil.DATE_FORMAT),DateUtil.DATE_FORMAT_HH_MM));
            responses.add(response);
        }
        return responses;
    }

    private void validateDataCreateAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        validateDataCommonCreateAsset(createAssetRequest);
        validateDataModuleCreateAsset(createAssetRequest);
        validateDataOriginalCreateAsset(createAssetRequest);
        validateDataDeclareCreateAsset(createAssetRequest);
    }

    private void validateDataDeclareCreateAsset(Map<String, Object> createAssetRequest) {
        declareServiceFactory.validateDataDeclare((Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET));
    }

    private void validateDataOriginalCreateAsset(Map<String, Object> createAssetRequest) {
       originalServiceFactory.validateDataOriginal((Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET));
    }

    private void validateDataModuleCreateAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        modulesServiceFactory.validateDataModules((List<Map<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE));
    }

    private void validateDataCommonCreateAsset(Map<String, Object> createAssetRequest) {
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
        Integer idDepartment = ValueUtil.getIntegerByObject(commonDataAsset.get("idDepartment"));
        departmentService.findDepartmentByIdDepartmentAndStatus(idDepartment, Constants.DEPARTMENT_ACTIVE_STATUS);
        Integer idLocation = ValueUtil.getIntegerByObject(commonDataAsset.get("idLocation"));
        locationService.findLocationByIdLocationAndIdDepartmentAndVisible(idLocation, idDepartment, Constants.LOCATION_ACTIVE_STATUS);
        Integer idAssetCategory = ValueUtil.getIntegerByObject(commonDataAsset.get("idAssetCategory"));
        assetCategoriesService.findAssetCategoriesByVisibleAndIdAssetCategory(idAssetCategory, Constants.ASSET_CATEGORY_IS_VISIBLE);
        Integer idUnit = ValueUtil.getIntegerByObject(commonDataAsset.get("idUnit"));
        unitsService.findUnitsByIdUnitAndStatus(idUnit, Constants.UNITS_IS_ACTIVE);
        Integer idDocumentsAttack = ValueUtil.getIntegerByObject(commonDataAsset.get("idDocumentAttack"));
        documentAttackService.findDocumentAttackByIdDocumentAndStatus(idDocumentsAttack, Constants.DOCUMENT_ATTACK_ACTIVE_STATUS);
        Integer idProject = ValueUtil.getIntegerByObject(commonDataAsset.get("idProjects"));
        projectsService.findProjectsByIdProjectAndStatus(idProject, Constants.PROJECTS_IS_VISIBLE);
    }

    private void storeNewAsset(Map<String, Object> createAssetRequest) throws ValidateFiledException {
        log.info("Init store asset");
        Asset asset = storeCommonData(createAssetRequest);
        storeModulesDataAsset(createAssetRequest, asset);
        storeOriginalDataAsset(createAssetRequest, asset);
        storeDeclareDataAsset(createAssetRequest, asset);
    }

    private void storeDeclareDataAsset(Map<String, Object> createAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Storing declare data asset");
        Map<String,Object> declareDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_DECLARE_ASSET);
        declareDataAsset.put("idAsset", asset.getIdAsset());
        DeclareFactory declareFactory = (DeclareFactory) ProxyInitDataAssetUtil.
                proxyInitDeclareDataAsset(ValueUtil.getStringByObject(declareDataAsset.get(Constants.KEY_TYPE_DECLARE)));
        IDeclare iDeclare = declareFactory.createDeclare(declareDataAsset);
        declareServiceFactory.save(iDeclare,declareDataAsset);
        log.info("Finish store declare factory " + declareFactory.getClass());
    }

    private void storeOriginalDataAsset(Map<String, Object> createAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Storing original data asset");
        Map<String,Object> originalDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_ORIGINAL_ASSET);
        originalDataAsset.put("idAsset", asset.getIdAsset());
        OriginalFactory originalFactory = (OriginalFactory) ProxyInitDataAssetUtil.
                proxyInitOriginalDataAsset(ValueUtil.getStringByObject(originalDataAsset.get(Constants.KEY_TYPE_ORIGINAL_ASSET)));
        IOriginal iOriginal = originalFactory.createOriginal(originalDataAsset);
        originalServiceFactory.save(iOriginal, originalDataAsset);
        log.info("Finish store original factory " + originalFactory.getClass());

    }

    private void storeModulesDataAsset(Map<String, Object> createAssetRequest, Asset asset) throws ValidateFiledException {
        log.info("Storing modules data asset");
        List<HashMap<String,Object>> modulesDataAsset = (List<HashMap<String,Object>>) createAssetRequest.get(Constants.KEY_MODULE);
        if (!CollectionUtils.isEmpty(modulesDataAsset)){
            for (HashMap<String, Object> moduleDataAsset : modulesDataAsset) {
                if (StringUtils.isNotBlank(ValueUtil.getStringByObject(moduleDataAsset.get("codeUser")))){
                    CsvcUser user = csvcUserService.findByCodeUser(ValueUtil.getStringByObject(moduleDataAsset.get("codeUser")));
                    moduleDataAsset.put("idUser", user.getIdUser());
                }
                moduleDataAsset.put("idAsset", asset.getIdAsset());
                ModuleFactory moduleFactory = (ModuleFactory) ProxyInitDataAssetUtil.
                        proxyInitModuleDataAsset(ValueUtil.getStringByObject(moduleDataAsset.get(Constants.KEY_TYPE_MODULE)));
                IModules iModules = moduleFactory.createModule(moduleDataAsset);
                modulesServiceFactory.save(iModules, moduleDataAsset);
                log.info("Finish store module factory " + moduleFactory.getClass());
            }
        }
    }

    private Asset storeCommonData(Map<String, Object> createAssetRequest) {
        log.info("Storing common data asset");
        Map<String,Object> commonDataAsset = (Map<String, Object>) createAssetRequest.get(Constants.KEY_COMMON);
        Asset asset = contructionDataAsset(commonDataAsset);
        assetRepository.save(asset);
        saveAssetOriginalOfFormations(asset, commonDataAsset);
        log.info("Store success common data asset by id = " + asset.getIdAsset());
        return asset;
    }

    private void saveAssetOriginalOfFormations(Asset asset, Map<String, Object> commonDataAsset) {
        List<HashMap<String,Object>> assetOriginalOfFormationData = (List<HashMap<String,Object>>) commonDataAsset.get(Constants.KEY_ASSET_ORIGINAL_OF_FORMATION);
        if (!CollectionUtils.isEmpty(assetOriginalOfFormationData)){
            List<AssetOriginalOfFormation>originalOfFormations = new ArrayList<>();
            String currentTime = String.valueOf(new Date().getTime());
            assetOriginalOfFormationData.forEach(obj -> {
                AssetOriginalOfFormation originalOfFormation = new AssetOriginalOfFormation();
                originalOfFormation.setIdOriginalOfFormation(ValueUtil.getIntegerByObject(obj.get("idOriginOfFormation")));
                originalOfFormation.setIdAsset(asset.getIdAsset());
                originalOfFormation.setTimeCreated(currentTime);
                originalOfFormation.setTimeModified(currentTime);
                originalOfFormation.setValue(ValueUtil.getStringByObject(obj.get("value")));
                originalOfFormations.add(originalOfFormation);
            });
            assetOriginalOfFormationService.saveAll(originalOfFormations);
        }
    }

    private Asset contructionDataAsset(Map<String, Object> dataAsset) {
        Asset asset = new Asset();
        asset.setName(ValueUtil.getStringByObject(dataAsset.get("name")));
        asset.setIdAssetCategory(ValueUtil.getIntegerByObject(dataAsset.get("idAssetCategory")));
        if (StringUtils.isNotBlank(ValueUtil.getStringByObject(dataAsset.get("codeDepartment")))) {
            asset.setCodeAsset(ValueUtil.getStringByObject(dataAsset.get("codeDepartment")) + "-" + UUID.randomUUID());
        } else {
            asset.setCodeAsset(String.valueOf(UUID.randomUUID()));
        }
        asset.setIdDocumentAttack(ValueUtil.getIntegerByObject(dataAsset.get("idDocumentAttack")));
        asset.setIdDepartment(ValueUtil.getIntegerByObject(dataAsset.get("idDepartment")));
        asset.setIdLocation(ValueUtil.getIntegerByObject(dataAsset.get("idLocation")));
        asset.setIdUnit(ValueUtil.getIntegerByObject(dataAsset.get("idUnit")));
        asset.setIdProjects(ValueUtil.getIntegerByObject(dataAsset.get("idProjects")));
        asset.setPurpose(ValueUtil.getStringByObject(dataAsset.get("purpose")));
        asset.setNotes(ValueUtil.getStringByObject(dataAsset.get("notes")));
        asset.setDescription(ValueUtil.getStringByObject(dataAsset.get("description")));
        asset.setQuantity(ValueUtil.getIntegerByObject(dataAsset.get("quantity")));
        asset.setFileAttack(ValueUtil.getStringByObject(dataAsset.get("fileAttack")));
        String timeCurrent = String.valueOf(new Date().getTime());
        asset.setTimeCreated(timeCurrent);
        asset.setTimeModified(timeCurrent);
        asset.setIdDepartmentDefault(ValueUtil.getIntegerByObject(dataAsset.get("idDepartmentDefault")));
        asset.setIdLevelTypeAsset(ValueUtil.getIntegerByObject(dataAsset.get("idLevelTypeAsset")));
        asset.setIdInstance(ValueUtil.getIntegerByObject(dataAsset.get("idInstance")));
        CsvcUser csvcUser = (CsvcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        asset.setIdUserCreated(csvcUser.getIdUser());
        asset.setIdUserModified(csvcUser.getIdUser());
        return asset;
    }
}
